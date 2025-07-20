package de.nick.recipeapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import de.nick.recipeapp.data.FavoritesRepository
import de.nick.recipeapp.data.api.MealApiService
import de.nick.recipeapp.util.ErrorUtils
import de.nick.recipeapp.views.BaseActivity
import de.nick.recipeapp.views.RecipeDetailActivity
import de.nick.recipeapp.views.RecipeListActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentWithBaseLayout(R.layout.activity_main)

        // Button to show favorite recipes
        val btnShowFavorites = findViewById<Button>(R.id.btnShowFavorites)
        // Button to show a random recipe fetched from the API
        val btnShowRandom = findViewById<Button>(R.id.btnShowRandom)

        // Input field to search recipes
        val searchInput = findViewById<EditText>(R.id.editTextSearch)
        // Button to submit search request
        val btnSearch = findViewById<Button>(R.id.btnSearch)

        btnShowFavorites.setOnClickListener {
            // Show favorite recipes
            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("showFavorites", true)
            startActivity(intent)
        }

        btnShowRandom.setOnClickListener {
            // Use coroutine to get a random recipe from the API in the background
            lifecycleScope.launch {
                val recipe = withContext(Dispatchers.IO) {
                    try {
                        MealApiService.getRandomMeal()
                    } catch (e: Exception) {
                        ErrorUtils.handleApiError(this@MainActivity, e)
                        null
                    }
                }

                if (recipe != null) {
                    // Continue only if recipe was fetched
                    val ingredientsText = recipe.ingredients.joinToString("\n") {
                        "- ${it.amount} ${it.name}"
                    }

                    // Open the detail screen for this recipe
                    val intent = Intent(this@MainActivity, RecipeDetailActivity::class.java).apply {
                        putExtra("id", recipe.id)
                        putExtra("title", recipe.name)
                        putExtra("description", recipe.description)
                        putExtra("imageUrl", recipe.imageUrl)
                        putExtra("ingredients", ingredientsText)
                    }
                    startActivity(intent)
                } else {
                    ErrorUtils.handleApiError(this@MainActivity, Exception("No random recipe found"))
                }
            }
        }

        btnSearch.setOnClickListener {
            val query = searchInput.text.toString().trim()

            // Only search if the input is not empty
            if (query.isNotBlank()) {
                lifecycleScope.launch {
                    val results = withContext(Dispatchers.IO) {
                        MealApiService.searchMeals(query)
                    }

                    when {
                        results == null -> {
                            // Show message for API error when results are null
                            ErrorUtils.handleApiError(this@MainActivity, Exception("API offline"))
                        }

                        results.isEmpty() -> {
                            // Show no results message when results are empty
                            ErrorUtils.showToast(this@MainActivity, getString(R.string.error_no_results))
                        }

                        else -> {
                            // Start recipe list with search query
                            val intent = Intent(this@MainActivity, RecipeListActivity::class.java)
                            intent.putExtra("searchQuery", query)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    // This screen does not need a back button
    override fun shouldShowBackButton(): Boolean = false
}
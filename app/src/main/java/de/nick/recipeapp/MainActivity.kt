package de.nick.recipeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import de.nick.recipeapp.data.api.MealApiService
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
                    MealApiService.getRandomMeal()
                }

                // Only continue if recipe was fetched
                recipe?.let {
                    // Combine ingredients into a single string
                    val ingredientsText = it.ingredients.joinToString("\n") { pair ->
                        "- ${pair.first} ${pair.second}"
                    }

                    // Open the detail screen for this recipe
                    val intent = Intent(this@MainActivity, RecipeDetailActivity::class.java).apply {
                        putExtra("id", it.id)
                        putExtra("title", it.name)
                        putExtra("description", it.description)
                        putExtra("imageUrl", it.imageUrl)
                        putExtra("ingredients", ingredientsText)
                    }
                    startActivity(intent)
                }
            }
        }

        btnSearch.setOnClickListener {
            val query = searchInput.text.toString()

            // Only search if the input is not empty
            if (query.isNotBlank()) {
                // Start recipe list with search query
                val intent = Intent(this, RecipeListActivity::class.java)
                intent.putExtra("searchQuery", query)
                startActivity(intent)
            }
        }
    }

    // This screen does not need a back button
    override fun shouldShowBackButton(): Boolean = false
}
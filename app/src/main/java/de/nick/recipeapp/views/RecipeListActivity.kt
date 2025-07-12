package de.nick.recipeapp.views

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.nick.recipeapp.R
import de.nick.recipeapp.data.FavoritesRepository
import de.nick.recipeapp.data.api.MealApiService
import de.nick.recipeapp.util.ErrorUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentWithBaseLayout(R.layout.activity_recipe_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRecipes)
        val noResultsText = findViewById<TextView>(R.id.textNoResults)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val query = intent.getStringExtra("searchQuery")
        val showFavorites = intent.getBooleanExtra("showFavorites", false)

        when {
            !query.isNullOrBlank() -> {
                // If search query exists, load search results with coroutine
                lifecycleScope.launch {
                    try {
                        val results = withContext(Dispatchers.IO) {
                            MealApiService.searchMeals(query)
                        }

                        when {
                            results == null -> {
                                // Show message for API error when results are null
                                ErrorUtils.handleApiError(
                                    this@RecipeListActivity,
                                    Exception("API offline")
                                )
                                recyclerView.visibility = View.GONE
                            }

                            results.isEmpty() -> {
                                // Show no results message when results are empty
                                ErrorUtils.showToast(
                                    this@RecipeListActivity,
                                    getString(R.string.error_no_results)
                                )
                                recyclerView.visibility = View.GONE
                            }

                            else -> {
                                // Show results if found
                                recyclerView.adapter = RecipeAdapter(results)
                                recyclerView.visibility = View.VISIBLE
                            }
                        }
                    } catch (e: Exception) {
                        // Catch: Show message for API error
                        ErrorUtils.handleApiError(this@RecipeListActivity, e)
                        recyclerView.visibility = View.GONE
                    }
                }
            }

            showFavorites -> {
                // Load saved recipes if favorites button was clicked
                FavoritesRepository.init(this)
                val favorites = FavoritesRepository.getAllFavorites()
                recyclerView.adapter = RecipeAdapter(favorites)
                noResultsText.visibility = if (favorites.isEmpty()) View.GONE else View.VISIBLE
            }

            else -> {
                // Show empty list if favorites are empty
                recyclerView.adapter = RecipeAdapter(emptyList())
                recyclerView.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        // Reload list of favorites when coming back from detail screen
        super.onResume()

        val showFavorites = intent.getBooleanExtra("showFavorites", false)
        if (showFavorites) {
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRecipes)
            val noResultsText = findViewById<TextView>(R.id.textNoResults)
            val updatedFavorites = FavoritesRepository.getAllFavorites()

            recyclerView.adapter = RecipeAdapter(updatedFavorites)
            noResultsText.visibility = if (updatedFavorites.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun shouldShowBackButton(): Boolean = true
}

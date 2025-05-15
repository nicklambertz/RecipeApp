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

        if (!query.isNullOrBlank()) {
            // If search query exists, load search results with coroutine
            lifecycleScope.launch {
                val results = withContext(Dispatchers.IO) {
                    MealApiService.searchMeals(query)
                }

                // Show results if found
                if (results.isNotEmpty()) {
                    recyclerView.adapter = RecipeAdapter(results)
                    noResultsText.visibility = View.GONE
                } else {
                    // Show "no results" text
                    noResultsText.visibility = View.VISIBLE
                }
            }
        } else if (showFavorites) {
            // Load saved recipes if favorites button was clicked
            FavoritesRepository.init(this)
            val favorites = FavoritesRepository.getAllFavorites()
            recyclerView.adapter = RecipeAdapter(favorites)
            noResultsText.visibility = if (favorites.isEmpty()) View.VISIBLE else View.GONE
        } else {
            // Show empty list if favorites are empty
            recyclerView.adapter = RecipeAdapter(emptyList())
            noResultsText.visibility = View.VISIBLE
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

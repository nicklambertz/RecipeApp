package de.nick.recipeapp.views

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.nick.recipeapp.R
import de.nick.recipeapp.data.RecipeRepository
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
        } else {
            // If no query given, show all local recipes
            val recipes = RecipeRepository.getRecipes()
            recyclerView.adapter = RecipeAdapter(recipes)
            noResultsText.visibility = if (recipes.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun shouldShowBackButton(): Boolean = true
}

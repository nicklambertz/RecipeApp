package de.nick.recipeapp.views

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.nick.recipeapp.R
import de.nick.recipeapp.data.RecipeRepository

class RecipeListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentWithBaseLayout(R.layout.activity_recipe_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRecipes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val recipes = RecipeRepository.getRecipes()
        recyclerView.adapter = RecipeAdapter(recipes)
    }

    override fun shouldShowBackButton(): Boolean = true
}
package de.nick.recipeapp.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.LinearLayout
import de.nick.recipeapp.R
import de.nick.recipeapp.data.RecipeRepository

class RecipeListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val contentView = layoutInflater.inflate(R.layout.activity_recipe_list, null)
        val container = findViewById<LinearLayout>(R.id.baseContainer)
        container.addView(contentView)

        val recyclerView = contentView.findViewById<RecyclerView>(R.id.recyclerViewRecipes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val recipes = RecipeRepository.getRecipes()
        recyclerView.adapter = RecipeAdapter(recipes)
    }
}
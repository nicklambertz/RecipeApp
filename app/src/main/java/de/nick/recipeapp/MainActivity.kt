package de.nick.recipeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import de.nick.recipeapp.views.BaseActivity
import de.nick.recipeapp.views.RecipeListActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentWithBaseLayout(R.layout.activity_main)

        val btnShowRecipes = findViewById<Button>(R.id.btnShowRecipes)
        btnShowRecipes.setOnClickListener {
            val intent = Intent(this, RecipeListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun shouldShowBackButton(): Boolean = false
}
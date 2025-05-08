package de.nick.recipeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import de.nick.recipeapp.views.RecipeListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val contentView = layoutInflater.inflate(R.layout.activity_main, null)

        val container = findViewById<LinearLayout>(R.id.baseContainer)
        container.addView(contentView)

        val btnShowRecipes = contentView.findViewById<Button>(R.id.btnShowRecipes)

        btnShowRecipes.setOnClickListener {
            val intent = Intent(this, RecipeListActivity::class.java)
            startActivity(intent)
        }
    }
}
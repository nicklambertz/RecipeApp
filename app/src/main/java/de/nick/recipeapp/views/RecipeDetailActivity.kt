package de.nick.recipeapp.views

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import coil.load
import de.nick.recipeapp.R

class RecipeDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentWithBaseLayout(R.layout.activity_recipe_detail)

        val imageView = findViewById<ImageView>(R.id.detailImage)
        val titleView = findViewById<TextView>(R.id.detailTitle)
        val ingredientsView = findViewById<TextView>(R.id.detailIngredients)
        val ingredients = intent.getStringExtra("ingredients") ?: ""
        val descriptionView = findViewById<TextView>(R.id.detailDescription)

        // Load values from intent
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val imageUrl = intent.getStringExtra("imageUrl")

        // Display recipe data
        titleView.text = title
        ingredientsView.text = ingredients
        descriptionView.text = description
        imageView.load(imageUrl) {
            placeholder(R.drawable.placeholder_image)
            error(R.drawable.error_image)
        }
    }

    override fun shouldShowBackButton(): Boolean = true
}
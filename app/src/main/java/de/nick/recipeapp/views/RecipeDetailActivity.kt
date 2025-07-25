package de.nick.recipeapp.views

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import coil.load
import de.nick.recipeapp.R
import de.nick.recipeapp.data.FavoritesRepository
import de.nick.recipeapp.data.Recipe
import de.nick.recipeapp.data.Ingredient
import de.nick.recipeapp.util.ErrorUtils

class RecipeDetailActivity : BaseActivity() {
    private lateinit var recipe: Recipe
    private lateinit var favoriteIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentWithBaseLayout(R.layout.activity_recipe_detail)

        FavoritesRepository.init(this)

        val imageView = findViewById<ImageView>(R.id.detailImage)
        val titleView = findViewById<TextView>(R.id.detailTitle)
        val ingredientsView = findViewById<TextView>(R.id.detailIngredients)
        val descriptionView = findViewById<TextView>(R.id.detailDescription)
        val starButton = findViewById<ImageButton>(R.id.btnFavorite)

        // Load values from intent
        val id = intent.getStringExtra("id") ?: ""
        val title = intent.getStringExtra("title") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""
        val ingredientsText = intent.getStringExtra("ingredients") ?: ""

        if (id.isEmpty() || title.isEmpty()) {
            ErrorUtils.handleApiError(this, Exception("Invalid recipe data"))
            finish()
            return
        }

        // Display recipe data
        titleView.text = title
        ingredientsView.text = ingredientsText
        descriptionView.text = description
        imageView.load(imageUrl) {
            placeholder(R.drawable.placeholder_image)
            error(R.drawable.error_image)
        }

        fun updateStarIcon() {
            val icon = if (FavoritesRepository.isFavorite(id)) {
                R.drawable.ic_star_filled
            } else {
                R.drawable.ic_star_border
            }
            starButton.setImageResource(icon)
        }

        updateStarIcon()

        starButton.setOnClickListener {
            try {
                val recipe = Recipe(
                    id, title, description, imageUrl,
                    ingredientsText.split("\n")
                        .filter { it.startsWith("- ") }
                        .map {
                            val parts = it.removePrefix("- ").split(" ", limit = 2)
                            Ingredient(parts[0], parts.getOrNull(1) ?: "")
                        }
                )

                if (FavoritesRepository.isFavorite(id)) {
                    FavoritesRepository.removeFromFavorites(id)
                } else {
                    FavoritesRepository.addToFavorites(recipe)
                }

                updateStarIcon()

            } catch (e: Exception) {
                ErrorUtils.handleFavoriteError(this, e)
            }
        }
    }

    override fun shouldShowBackButton(): Boolean = true
}
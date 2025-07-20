package de.nick.recipeapp.views

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.nick.recipeapp.R
import de.nick.recipeapp.data.Recipe

class RecipeAdapter(private val recipes: List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageViewRecipe)
        val name: TextView = view.findViewById(R.id.textViewRecipeName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.name.text = recipe.name
        // Load the recipe image with fallback
        holder.image.load(recipe.imageUrl) {
            crossfade(true)
            placeholder(R.drawable.placeholder_image)
            error(R.drawable.error_image)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context

            // Format ingredients into text for detail screen
            val ingredientsText = recipe.ingredients.joinToString("\n") {
                "- ${it.amount} ${it.name}"
            }

            // Open detail activity for clicked recipe
            val intent = Intent(context, RecipeDetailActivity::class.java).apply {
                putExtra("id", recipe.id)
                putExtra("title", recipe.name)
                putExtra("description", recipe.description)
                putExtra("imageUrl", recipe.imageUrl)
                putExtra("ingredients", ingredientsText)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = recipes.size
}
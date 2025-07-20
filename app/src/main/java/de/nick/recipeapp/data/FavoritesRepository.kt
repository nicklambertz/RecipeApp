package de.nick.recipeapp.data

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object FavoritesRepository {
    private const val PREFS_NAME = "favorites_prefs"
    private const val FAVORITES_KEY = "favorites"

    private lateinit var prefs: SharedPreferences
    private val moshi = Moshi.Builder().build()

    // List<Recipe> Adapter
    private val type = Types.newParameterizedType(List::class.java, Recipe::class.java)
    private val adapter = moshi.adapter<List<Recipe>>(type)

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun addToFavorites(recipe: Recipe) {
        val favorites = getAllFavorites().toMutableList()
        if (favorites.none { it.id == recipe.id }) {
            favorites.add(recipe)
            saveFavorites(favorites)
        }
    }

    fun removeFromFavorites(id: String) {
        val updated = getAllFavorites().filterNot { it.id == id }
        saveFavorites(updated)
    }

    fun isFavorite(id: String): Boolean {
        return getAllFavorites().any { it.id == id }
    }

    fun getAllFavorites(): List<Recipe> {
        val json = prefs.getString(FAVORITES_KEY, null) ?: return emptyList()
        return adapter.fromJson(json) ?: emptyList()
    }

    private fun saveFavorites(favorites: List<Recipe>) {
        val json = adapter.toJson(favorites)
        prefs.edit().putString(FAVORITES_KEY, json).apply()
    }
}
package de.nick.recipeapp.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavoritesRepository {
    private const val PREFS_NAME = "favorites_prefs"
    private const val FAVORITES_KEY = "favorites"

    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

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
        val favorites = getAllFavorites().filterNot { it.id == id }
        saveFavorites(favorites)
    }

    fun isFavorite(id: String): Boolean {
        return getAllFavorites().any { it.id == id }
    }

    fun getAllFavorites(): List<Recipe> {
        val json = prefs.getString(FAVORITES_KEY, "[]")
        val type = object : TypeToken<List<Recipe>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun saveFavorites(favorites: List<Recipe>) {
        prefs.edit().putString(FAVORITES_KEY, gson.toJson(favorites)).apply()
    }
}
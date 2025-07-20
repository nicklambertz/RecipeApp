package de.nick.recipeapp.data.api


import android.util.Log
import de.nick.recipeapp.data.Recipe

object MealApiService {

    private const val TAG = "MealApiService"

    // Search for recipes that match the user's query
    suspend fun searchMeals(query: String): List<Recipe>? {
        return try {
            // Search with user query, return empty list if the query gets no results
            val response = RetrofitClient.api.searchMeals(query)
            response.meals?.mapNotNull { it.toRecipe() } ?: emptyList()
        } catch (e: Exception) {
            // Return error message when the API request does not work
            Log.e(TAG, "Error while searching: ${e.message}", e)
            null
        }
    }

    // Search for a random recipe
    suspend fun getRandomMeal(): Recipe? {
        return try {
            val response = RetrofitClient.api.getRandomMeal()
            response.meals?.mapNotNull { it.toRecipe() }?.firstOrNull()
        } catch (e: Exception) {
            // Return error message when the API request does not work
            Log.e(TAG, "Error for random recipe: ${e.message}", e)
            null
        }
    }
}
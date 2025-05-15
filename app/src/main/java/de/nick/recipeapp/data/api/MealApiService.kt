package de.nick.recipeapp.data.api

import android.util.Log
import de.nick.recipeapp.data.Recipe
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object MealApiService {

    private const val TAG = "MealApiService"

    // Get one random recipe from the API
    fun getRandomMeal(): Recipe? {
        val urlString = "https://www.themealdb.com/api/json/v1/1/random.php"
        return fetchSingleMeal(urlString)
    }

    // Search for recipes that match the user's query
    fun searchMeals(query: String): List<Recipe> {
        val urlString = "https://www.themealdb.com/api/json/v1/1/search.php?s=${query.trim()}"
        return fetchMealList(urlString)
    }

    // Fetch a single recipe from the given API URL
    private fun fetchSingleMeal(urlString: String): Recipe? {
        return try {
            val connection = URL(urlString).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val input = BufferedReader(InputStreamReader(connection.inputStream))
            val response = input.readText()
            input.close()

            val json = JSONObject(response)
            val meals = json.getJSONArray("meals")

            // Return first meal if available
            if (meals.length() > 0) {
                val mealJson = meals.getJSONObject(0)
                jsonToRecipe(mealJson)
            } else null
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching meal: ${e.message}", e)
            null
        }
    }

    // Fetch a list of recipes from the given search URL
    private fun fetchMealList(urlString: String): List<Recipe> {
        return try {
            val connection = URL(urlString).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val input = BufferedReader(InputStreamReader(connection.inputStream))
            val response = input.readText()
            input.close()

            val json = JSONObject(response)
            val mealsArray = json.optJSONArray("meals") ?: return emptyList()

            // Convert JSON array to list of Recipe objects
            List(mealsArray.length()) { i ->
                val mealJson = mealsArray.getJSONObject(i)
                jsonToRecipe(mealJson)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error searching meals: ${e.message}", e)
            emptyList()
        }
    }

    // Convert JSON data to a Recipe object
    private fun jsonToRecipe(json: JSONObject): Recipe {
        val id = json.optString("idMeal") ?: ""
        val name = json.optString("strMeal") ?: "Unknown"
        val description = json.optString("strInstructions") ?: ""
        val imageUrl = json.optString("strMealThumb") ?: ""

        // Loop through 20 possible ingredients, make sure both strings are not nullable
        val ingredients = mutableListOf<Pair<String, String>>()
        for (i in 1..20) {
            val ingredient = json.optString("strIngredient$i")?.trim() ?: ""
            val measure = json.optString("strMeasure$i")?.trim() ?: ""
            if (ingredient.isNotBlank() && ingredient.lowercase() != "null") {
                ingredients.add(measure to ingredient)
            }
        }

        return Recipe(id, name, description, imageUrl, ingredients)
    }
}
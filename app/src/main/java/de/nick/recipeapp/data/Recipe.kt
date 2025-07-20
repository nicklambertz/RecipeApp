package de.nick.recipeapp.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(
    @Json(name = "idMeal") val id: String,
    @Json(name = "strMeal") val name: String,
    @Json(name = "strInstructions") val description: String,
    @Json(name = "strMealThumb") val imageUrl: String,
    // List of ingredients will be build in a separate file because response of API is not structured
    val ingredients: List<Ingredient> = emptyList()
)

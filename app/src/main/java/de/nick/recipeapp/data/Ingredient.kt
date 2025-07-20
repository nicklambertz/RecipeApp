package de.nick.recipeapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ingredient(
    val amount: String = "",
    val name: String = ""
)
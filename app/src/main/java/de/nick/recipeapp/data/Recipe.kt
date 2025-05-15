package de.nick.recipeapp.data

data class Recipe(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val ingredients: List<Pair<String, String>>
)
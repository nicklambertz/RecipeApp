package de.nick.recipeapp.data

object RecipeRepository {
    fun getRecipes(): List<Recipe> {
        return listOf(
            Recipe(1, "Spaghetti Carbonara", "Leckere Spaghetti...", "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg/medium"),
            Recipe(2, "Burger", "Frischer Burger...", "https://www.themealdb.com/images/media/meals/vdwloy1713225718.jpg"),
            Recipe(3, "ErrorTest", "Fehlermeldung...", "https://example.com/sushi.jpg")
        )
    }


}

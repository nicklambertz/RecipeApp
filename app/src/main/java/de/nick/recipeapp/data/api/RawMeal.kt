package de.nick.recipeapp.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.nick.recipeapp.data.Ingredient
import de.nick.recipeapp.data.Recipe


@JsonClass(generateAdapter = true)
data class RawMeal(
    @Json(name = "idMeal") val id: String?,
    @Json(name = "strMeal") val name: String?,
    @Json(name = "strInstructions") val description: String?,
    @Json(name = "strMealThumb") val imageUrl: String?,

    @Json(name = "strIngredient1") val ingredient1: String?,
    @Json(name = "strIngredient2") val ingredient2: String?,
    @Json(name = "strIngredient3") val ingredient3: String?,
    @Json(name = "strIngredient4") val ingredient4: String?,
    @Json(name = "strIngredient5") val ingredient5: String?,
    @Json(name = "strIngredient6") val ingredient6: String?,
    @Json(name = "strIngredient7") val ingredient7: String?,
    @Json(name = "strIngredient8") val ingredient8: String?,
    @Json(name = "strIngredient9") val ingredient9: String?,
    @Json(name = "strIngredient10") val ingredient10: String?,

    @Json(name = "strMeasure1") val measure1: String?,
    @Json(name = "strMeasure2") val measure2: String?,
    @Json(name = "strMeasure3") val measure3: String?,
    @Json(name = "strMeasure4") val measure4: String?,
    @Json(name = "strMeasure5") val measure5: String?,
    @Json(name = "strMeasure6") val measure6: String?,
    @Json(name = "strMeasure7") val measure7: String?,
    @Json(name = "strMeasure8") val measure8: String?,
    @Json(name = "strMeasure9") val measure9: String?,
    @Json(name = "strMeasure10") val measure10: String?
)

@JsonClass(generateAdapter = true)
data class RawMealResponse(
    @Json(name = "meals")
    val meals: List<RawMeal>?
)

fun RawMeal.toRecipe(): Recipe? {
    if (id == null || name == null || description == null || imageUrl == null) return null

    val ingredients = listOfNotNull(
        ingredient1 to measure1,
        ingredient2 to measure2,
        ingredient3 to measure3,
        ingredient4 to measure4,
        ingredient5 to measure5,
        ingredient6 to measure6,
        ingredient7 to measure7,
        ingredient8 to measure8,
        ingredient9 to measure9,
        ingredient10 to measure10,
    ).filter { it.first?.isNotBlank() == true }
        .map { (ing, meas) ->
            Ingredient(meas?.trim() ?: "", ing?.trim() ?: "")
        }

    return Recipe(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        ingredients = ingredients
    )
}
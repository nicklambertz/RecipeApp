package de.nick.recipeapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): RawMealResponse

    @GET("random.php")
    suspend fun getRandomMeal(): RawMealResponse
}
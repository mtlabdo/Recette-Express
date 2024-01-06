package com.example.data.androidTest.remote.api

import com.example.data.androidTest.remote.model.RecipeDetailResponse
import com.example.data.androidTest.remote.model.RecipesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesService {

    @GET("/api/json/v1/1/filter.php?i=")
    suspend fun getRecipes(@Query("s") category: String): Response<RecipesResponse?>

    @GET("/api/json/v1/1/lookup.php")
    suspend fun getRecipeDetail(@Query("i") recipeId: String): Response<RecipeDetailResponse?>
}
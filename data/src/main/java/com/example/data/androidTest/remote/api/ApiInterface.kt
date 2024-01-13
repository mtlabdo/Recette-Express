package com.example.data.androidTest.remote.api

import com.example.data.androidTest.remote.model.RecipeDetailResponseNetwork
import com.example.data.androidTest.remote.model.RecipesResponseNetwork
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/api/json/v1/1/filter.php?i=")
    suspend fun getRecipes(@Query("s") category: String): Response<RecipesResponseNetwork?>

    @GET("/api/json/v1/1/lookup.php")
    suspend fun getRecipeDetail(@Query("i") recipeId: String): Response<RecipeDetailResponseNetwork?>
}
package com.example.data.androidTest.remote.model

data class RecipesResponse(
    val meals : List<Meal> = emptyList()
)

data class Meal(
    val idMeal : String,
    val strMeal : String,
    val strMealThumb : String?,
)
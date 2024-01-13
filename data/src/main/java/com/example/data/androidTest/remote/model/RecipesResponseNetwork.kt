package com.example.data.androidTest.remote.model

data class RecipesResponseNetwork(
    val meals : List<RecipeNetwork> = emptyList()
)

data class RecipeNetwork(
    val idMeal : String,
    val strMeal : String,
    val strMealThumb : String?,
)
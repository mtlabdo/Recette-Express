package com.example.data.androidTest.remote.model

data class RecipeDetailResponseNetwork(
    val meals: List<RecipeDetailNetwork> = emptyList()
)

data class RecipeDetailNetwork(
    val idMeal: String,
    val strMeal: String,
    val strArea: String?,
    val strInstructions: String?,
    val strDrinkAlternate: String?,
    val strCategory: String?,
    val strMealThumb: String?,
    val strYoutube: String?,
)
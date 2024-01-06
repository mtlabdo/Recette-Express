package com.example.data.androidTest.remote.model

data class RecipeDetailResponse(
    val meals: List<MealDetail> = emptyList()
)

data class MealDetail(
    val idMeal: String,
    val strMeal: String,
    val strArea: String?,
    val strInstructions: String?,
    val strDrinkAlternate: String?,
    val strCategory: String?,
    val strMealThumb: String?,
    val strYoutube: String?,
)
package com.exemple.androidTest.core.model

data class RecipeDetail (
    val idMeal: String,
    val strMeal: String? = null,
    val strMealThumb: String? = null,
    val strCategory: String? = null,
    val strArea: String? = null,
    val strInstructions: String? = null,
    val strYoutube: String? = null
)
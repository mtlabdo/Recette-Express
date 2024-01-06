package com.example.androidTest.mapper

import com.example.data.androidTest.remote.model.MealDetail
import com.exemple.androidTest.core.model.RecipeDetail

fun MealDetail.toDataModel() = RecipeDetail(
    idMeal = this.idMeal,
    strMeal = this.strMeal,
    strCategory = this.strCategory,
    strArea = this.strArea,
    strInstructions = this.strInstructions,
    strMealThumb = this.strMealThumb,
    strYoutube = this.strYoutube
)


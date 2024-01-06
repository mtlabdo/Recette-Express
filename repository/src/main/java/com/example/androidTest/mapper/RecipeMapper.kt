package com.example.androidTest.mapper

import com.example.data.androidTest.remote.model.Meal
import com.exemple.androidTest.core.model.Recipe

fun Meal.toDataModel() = Recipe(
    idMeal = this.idMeal,
    strMeal = this.strMeal,
    strMealThumb = this.strMealThumb
)


package com.example.androidTest.mapper

import com.example.data.androidTest.remote.model.RecipeDetailNetwork
import com.example.data.androidTest.remote.model.RecipeNetwork
import com.exemple.androidTest.core.model.Recipe
import com.exemple.androidTest.core.model.RecipeDetail
import com.example.data.androidTest.database.entity.RecipeEntity

fun RecipeDetailNetwork.toDataModel() = RecipeDetail(
    idMeal = this.idMeal,
    strMeal = this.strMeal,
    strCategory = this.strCategory,
    strArea = this.strArea,
    strInstructions = this.strInstructions,
    strMealThumb = this.strMealThumb,
    strYoutube = this.strYoutube
)


////////////////////////////////

fun RecipeNetwork.toEntityModel() = RecipeEntity(
    idRecipe = idMeal,
    name = strMeal,
    imageUrl = strMealThumb,
)

fun List<RecipeNetwork>.toListEntityModel() = this.map {
    it.toEntityModel()
}


////////////////////////////////

fun RecipeEntity.toDataModel() = Recipe(
    idMeal = idRecipe,
    name = name,
    image = imageUrl
)


fun List<RecipeEntity>.toListDataModel() = this.map {
    it.toDataModel()
}

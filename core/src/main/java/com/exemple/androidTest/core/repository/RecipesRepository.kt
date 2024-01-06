package com.exemple.androidTest.core.repository

import com.exemple.androidTest.core.model.Recipe
import com.exemple.androidTest.core.model.RecipeDetail
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    fun getAllRecipes(): Flow<DataState<List<Recipe>>>

    fun getRecipeById(recipeId: String): Flow<DataState<RecipeDetail>>
}
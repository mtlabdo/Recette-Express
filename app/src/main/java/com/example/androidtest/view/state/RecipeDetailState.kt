package com.example.androidtest.view.state

import com.exemple.androidTest.core.model.RecipeDetail

sealed interface RecipeDetailViewState {

    data object Loading : RecipeDetailViewState

    data class Success(
        val recipeDetail: RecipeDetail
    ) : RecipeDetailViewState

    data class Error(val error: String) : RecipeDetailViewState
}
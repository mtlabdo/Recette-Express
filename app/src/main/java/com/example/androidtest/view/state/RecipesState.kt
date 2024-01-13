package com.example.androidtest.view.state

import com.exemple.androidTest.core.model.Recipe

sealed interface RecipesViewState {

    data object Loading : RecipesViewState

    data class Success(
        val recipes: List<Recipe>
    ) : RecipesViewState

    data class Error(val error: String) : RecipesViewState
}


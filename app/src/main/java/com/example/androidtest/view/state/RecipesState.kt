package com.example.androidtest.view.state

import com.exemple.androidTest.core.model.Recipe

class RecipesState(
    var isLoading: Boolean = false,
    var recipes: List<Recipe> = emptyList(),
    var error: String? = null,
    var isConnectivityAvailable: Boolean? = null,
) : State {


    companion object {
        val initialState = RecipesState(
            isLoading = false,
            recipes = emptyList(),
            error = null,
            isConnectivityAvailable = null
        )
    }

}


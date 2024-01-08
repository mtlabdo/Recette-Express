package com.example.androidtest.view.state

import com.exemple.androidTest.core.model.Recipe

class RecipesState(
    var isLoading: Boolean = false,
    var recipes: List<Recipe> = emptyList(),
    var error: String? = null,
    var isConnectivityAvailable: Boolean? = null,
) : State {

    fun copyState(
        isLoading: Boolean = this.isLoading,
        data: List<Recipe> = this.recipes,
        error: String? = this.error,
        isConnectivityAvailable: Boolean? = this.isConnectivityAvailable
    ): RecipesState {
        return RecipesState(isLoading = isLoading, recipes = data, error = error, isConnectivityAvailable =  isConnectivityAvailable)
    }

    companion object {
        val initialState = RecipesState(
            isLoading = false,
            recipes = emptyList(),
            error = null,
            isConnectivityAvailable = null
        )
    }

}


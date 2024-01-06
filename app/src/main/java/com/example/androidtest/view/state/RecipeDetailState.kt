package com.example.androidtest.view.state

import com.exemple.androidTest.core.model.RecipeDetail

class RecipeDetailState(
    var isLoading: Boolean = false,
    var error: String? = null,
    var recipeDetail: RecipeDetail? = null,
    var isConnectivityAvailable: Boolean? = null,
) : State {

    companion object {
        val initialState = RecipeDetailState(
            isLoading = false,
            error = null,
            recipeDetail = null,
            isConnectivityAvailable = null
        )
    }

}
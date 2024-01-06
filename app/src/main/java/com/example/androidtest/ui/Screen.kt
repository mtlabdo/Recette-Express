package com.example.androidtest.ui

sealed class Screen(val route: String, val name: String) {

    object Recipes : Screen("recipes", "Recipes")
    object RecipeDetail : Screen("recipe/{recipeId}", "Recipe details") {
        fun route(recipeId: String) = "recipe/$recipeId"

        const val ARG_RECIPE_ID: String = "recipeId"
    }
}

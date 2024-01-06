package com.example.androidtest.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidtest.ui.Screen
import com.example.androidtest.ui.screens.RecipeDetailScreen
import com.example.androidtest.ui.screens.RecipesScreen
import com.example.androidtest.view.viewmodel.RecipeDetailViewModel
import com.example.androidtest.view.viewmodel.RecipesViewModel

const val RECIPES_NAV_HOST_ROUTE = "recipe-main-route"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = Screen.Recipes.route,
        route = RECIPES_NAV_HOST_ROUTE
    ) {

        composable(Screen.Recipes.route) {
            RecipesScreen(
                viewModel = viewModel(
                    factory = RecipesViewModel.providedFactory
                ),
                onNavigateToRecipeDetail = { navController.navigateToNoteDetail(it) },
            )
        }

        composable(Screen.RecipeDetail.route,
            arguments = listOf(
                navArgument(Screen.RecipeDetail.ARG_RECIPE_ID) { type = NavType.StringType }
            )
        ) {
            val recipeId =
                requireNotNull(it.arguments?.getString(Screen.RecipeDetail.ARG_RECIPE_ID))
            RecipeDetailScreen(
                viewModel = viewModel(
                    factory = RecipeDetailViewModel.providedFactory(recipeId)
                ),
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

/**
 * Launches recipe detail screen for specified [recipeId]
 */
fun NavController.navigateToNoteDetail(noteId: String) = navigate(Screen.RecipeDetail.route(noteId))



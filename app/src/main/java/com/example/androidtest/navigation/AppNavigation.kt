package com.example.androidtest.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidtest.ui.Screen
import com.example.androidtest.ui.screens.RecipeDetailScreen
import com.example.androidtest.ui.screens.RecipesScreen
import com.example.androidtest.utils.assistedViewModel
import com.example.androidtest.view.viewmodel.RecipeDetailViewModel
import com.exemple.androidTest.core.dispatcher.DispatcherProvider

const val RECIPES_NAV_HOST_ROUTE = "recipe-main-route"


@Composable
fun AppNavigation(dispatcherProvider: DispatcherProvider) {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = Screen.Recipes.route,
        route = RECIPES_NAV_HOST_ROUTE
    ) {

        composable(Screen.Recipes.route) {
            RecipesScreen(
                viewModel = hiltViewModel(),
                coroutineDispatcher = dispatcherProvider,
                onNavigateToRecipeDetail = { navController.navigateToRecipeDetail(it) },
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

                viewModel = assistedViewModel {
                    RecipeDetailViewModel.provideFactory(
                        recipeDetailViewModelFactory(),
                        recipeId
                    )
                },
                coroutineDispatcher = dispatcherProvider,
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

/**
 * Launches recipe detail screen for specified [recipeId]
 */
fun NavController.navigateToRecipeDetail(recipeId: String) = navigate(Screen.RecipeDetail.route(recipeId))



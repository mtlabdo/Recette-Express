package com.example.androidTest.repository

import com.example.androidTest.mapper.toDataModel
import com.example.data.androidTest.remote.api.RecipesService
import com.example.data.androidTest.remote.util.getResponse
import com.exemple.androidTest.core.model.Recipe
import com.exemple.androidTest.core.model.RecipeDetail
import com.exemple.androidTest.core.repository.DataState
import com.exemple.androidTest.core.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipesRemoteRepository @Inject internal constructor(
    private val recipesService: RecipesService
) : RecipesRepository {

    // TODO MANAGE ERRORS (API don't have generic response)
    override fun getAllRecipes(): Flow<DataState<List<Recipe>>> = flow {
        val recipesResponse = recipesService.getRecipes("").getResponse()
        val dataState =
            if (recipesResponse?.meals != null) DataState.Success(recipesResponse.meals.map { it.toDataModel() })
            else DataState.Error("Un problème est survenu lors de la récuperation des recettes")
        emit(dataState)
    }.catch {
        emit(DataState.Error("Un problème est survenu lors de la récuperation des recettes"))
    }

    // TODO MANAGE ERRORS (API don't have generic response)
    override fun getRecipeById(recipeId: String): Flow<DataState<RecipeDetail>> = flow {
        val recipeDetailResponse = recipesService.getRecipeDetail(recipeId).getResponse()
        val dataState = if (recipeDetailResponse?.meals?.isNotEmpty() == true) DataState.Success(
            recipeDetailResponse.meals.first().toDataModel()
        )
        else DataState.Error("Impossible de trouver les détails de la recette.")
        emit(dataState)
    }.catch {
        emit(DataState.Error("Impossible de trouver les détails de la recette."))
    }
}

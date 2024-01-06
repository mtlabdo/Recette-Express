package com.example.androidTest.repository

import com.example.androidTest.mapper.toDataModel
import com.example.data.androidTest.remote.api.RecipesService
import com.example.data.androidTest.remote.retrofit.RetrofitClient
import com.example.data.androidTest.remote.util.getResponse
import com.exemple.androidTest.core.model.Recipe
import com.exemple.androidTest.core.model.RecipeDetail
import com.exemple.androidTest.core.repository.DataState
import com.exemple.androidTest.core.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RecipesRemoteRepository(
) : RecipesRepository {

    private val recipesService: RecipesService = RetrofitClient().getRetrofit<RecipesService>()

    // TODO MANAGE ERRORS (API don't have generic response)
    override fun getAllRecipes(): Flow<DataState<List<Recipe>>> = flow {
        val recipesResponse = recipesService.getRecipes("")?.getResponse()
        val dataState =
            if (recipesResponse?.meals != null) DataState.Success(recipesResponse.meals.map { it.toDataModel() })
            else DataState.Error("Can't get latest recipes")
        emit(dataState)
    }.catch {
        emit(DataState.Error("Can't get latest recipes "))
    }

    // TODO MANAGE ERRORS (API don't have generic response)
    override fun getRecipeById(recipeId: String): Flow<DataState<RecipeDetail>> = flow {
        val recipeDetailResponse = recipesService.getRecipeDetail(recipeId)?.getResponse()
        val dataState = if (recipeDetailResponse?.meals?.isNotEmpty() == true) DataState.Success(
            recipeDetailResponse.meals.first().toDataModel()
        )
        else DataState.Error("Can't found recipe detail")
        emit(dataState)
    }.catch {
        emit(DataState.Error("Can't get latest recipes "))
    }
}

package com.example.androidTest.repository

import com.example.androidTest.mapper.toDataModel
import com.example.androidTest.mapper.toListDataModel
import com.example.androidTest.mapper.toListEntityModel
import com.example.data.androidTest.database.RecipesDbService
import com.example.data.androidTest.remote.api.ApiInterface
import com.example.data.androidTest.remote.util.getResponse
import com.exemple.androidTest.core.model.Recipe
import com.exemple.androidTest.core.model.RecipeDetail
import com.exemple.androidTest.core.repository.DataState
import com.exemple.androidTest.core.repository.ErrorHolder
import com.exemple.androidTest.core.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipesRemoteRepository @Inject internal constructor(
    private val database: RecipesDbService,
    private val recipesService: ApiInterface
) : RecipesRepository {

    override fun getAllRecipes(): Flow<DataState<List<Recipe>>> = flow {
        try {
            val recipesResponse = recipesService.getRecipes("").getResponse()
            if (recipesResponse?.meals != null) {
                val recipes = recipesResponse.meals.toListEntityModel();
                database.deleteAllAndInsert(recipes)
                emit(DataState.success(recipes.toListDataModel()))
            } else {
                emitLocalRecipes()
            }
        } catch (e: UnknownHostException) {
            emitLocalRecipes()
        } catch (generalException: Exception) {
            emit(DataState.Failure(ErrorHolder.Unknown("Un problème est survenu lors de la récuperation des recettes")))
        }
    }

    private suspend fun FlowCollector<DataState<List<Recipe>>>.emitLocalRecipes() {
        val localRecipes: List<Recipe> = database.getAllRecipes().first().toListDataModel()
        emit(DataState.Success(localRecipes))
    }

    // TODO MANAGE ERRORS (API don't have generic response)
    override fun getRecipeById(recipeId: String): Flow<DataState<RecipeDetail>> = flow {
        val recipeDetailResponse = recipesService.getRecipeDetail(recipeId).getResponse()
        val dataState = if (recipeDetailResponse?.meals?.isNotEmpty() == true) DataState.Success(
            recipeDetailResponse.meals.first().toDataModel()
        )
        else DataState.Failure(ErrorHolder.Unknown("Impossible de trouver les détails de la recette."))
        emit(dataState)
    }.catch {
        emit(DataState.Failure(ErrorHolder.Unknown("Impossible de trouver les détails de la recette.")))
    }
}

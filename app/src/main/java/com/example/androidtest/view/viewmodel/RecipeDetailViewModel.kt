package com.example.androidtest.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidTest.repository.RecipesRemoteRepository
import com.example.androidtest.view.state.RecipeDetailState
import com.exemple.androidTest.core.model.RecipeDetail
import com.exemple.androidTest.core.repository.RecipesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val recipesRepository: RecipesRepository,
    private val recipeId: String
) : BaseViewModel<RecipeDetailState>() {

    override val state: MutableStateFlow<RecipeDetailState> =
        MutableStateFlow(RecipeDetailState.initialState)

    private var syncJob: Job? = null

    init {
        getRecipeDetail()
    }

    private fun getRecipeDetail() {
        if (syncJob?.isActive == true) return


        syncJob = viewModelScope.launch {
            try {
                setLoadingState()
                val recipe = recipesRepository.getRecipeById(recipeId).firstOrNull()
                if (recipe != null) {
                    recipe.onSuccess {
                        setSuccessState(it)
                    }
                    recipe.onFailure {
                        setErrorState(it)
                    }
                } else {
                    setErrorState("No item found with this ID: $recipeId")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Can not get recipes")
                setErrorState("\"Can not get recipe Detail\"")
            }
        }
    }


    private fun setLoadingState() {
        val newState = state.value.copyState()
        newState.isLoading = true
        newState.recipeDetail = null
        newState.error = null
        state.value = newState
    }

    private fun setSuccessState(recipe: RecipeDetail) {
        val newState = state.value.copyState()
        newState.isLoading = false
        newState.recipeDetail = recipe
        newState.error = null
        state.value = newState
    }

    private fun setErrorState(errorMessage: String) {
        val newState = state.value.copyState()
        newState.isLoading = false
        newState.recipeDetail = null
        newState.error = errorMessage
        state.value = newState
    }


    private fun RecipeDetailState.copyState(): RecipeDetailState {
        return RecipeDetailState()
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        private const val TAG = "RecipeDetailViewModel"

        fun providedFactory(recipeId: String): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeDetailViewModel(RecipesRemoteRepository(), recipeId) as T
                }
            }
        }
    }
}
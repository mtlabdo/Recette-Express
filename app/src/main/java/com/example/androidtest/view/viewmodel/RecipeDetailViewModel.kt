package com.example.androidtest.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidtest.view.state.RecipeDetailState
import com.exemple.androidTest.core.model.RecipeDetail
import com.exemple.androidTest.core.repository.RecipesRepository
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeDetailViewModel @AssistedInject constructor(
    private val recipesRepository: RecipesRepository,
    @Assisted private val recipeId: String
) : BaseViewModel<RecipeDetailState>() {

    override val state: MutableStateFlow<RecipeDetailState> =
        MutableStateFlow(RecipeDetailState.initialState)

    private var syncJob: Job? = null

    init {
        getRecipeDetail()
    }

    fun getRecipeDetail() {
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
                    setErrorState("No item found with this ID: ")
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

    @AssistedFactory
    interface Factory {
        fun create(recipeId: String): RecipeDetailViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        private const val TAG = "RecipeDetailViewModel"

        fun provideFactory(
            assistedFactory: Factory,
            recipeId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(recipeId) as T
            }
        }
    }
}
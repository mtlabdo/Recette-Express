package com.example.androidtest.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidtest.view.state.RecipeDetailViewState
import com.exemple.androidTest.core.dispatcher.DispatcherProvider
import com.exemple.androidTest.core.repository.RecipesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RecipeDetailViewModel @AssistedInject constructor(
    private val recipesRepository: RecipesRepository,
    @Assisted private val recipeId: String,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<RecipeDetailViewState>() {

    override val initialViewState = RecipeDetailViewState.Loading

    private var syncJob: Job? = null

    init {
        getRecipeDetail()
    }

    fun getRecipeDetail() {
        if (syncJob?.isActive == true) return
        syncJob = viewModelScope.launch {
            try {
                recipesRepository.getRecipeById(recipeId)
                    .onStart { updateViewState(RecipeDetailViewState.Loading) }
                    .map { it ->
                        it.onSuccess { recipeDetail ->
                            updateViewState(RecipeDetailViewState.Success(recipeDetail))
                        }
                        it.onFailure { error ->
                            updateViewState(RecipeDetailViewState.Error(error.message))
                        }
                    }
                    .flowOn(dispatcherProvider.io)
                    .launchIn(viewModelScope)
            } catch (e: Exception) {
                e.printStackTrace()
                updateViewState(RecipeDetailViewState.Error("Can not get recipe Detail"))
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(recipeId: String): RecipeDetailViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        private const val TAG = "RecipeDetailViewModel"

        fun provideFactory(
            assistedFactory: Factory, recipeId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(recipeId) as T
            }
        }
    }
}
package com.example.androidtest.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidTest.repository.RecipesRemoteRepository
import com.example.androidtest.view.state.RecipesState
import com.exemple.androidTest.core.model.Recipe
import com.exemple.androidTest.core.repository.RecipesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RecipesViewModel(
    private val recipesRepository: RecipesRepository,
) : BaseViewModel<RecipesState>() {

    override val state: MutableStateFlow<RecipesState> = MutableStateFlow(RecipesState.initialState)

    private var syncJob: Job? = null

    init {
        getRecipes()
    }


    private fun getRecipes() {

        if (syncJob?.isActive == true) return
        syncJob = viewModelScope.launch {
            try {
                recipesRepository.getAllRecipes()
                    .distinctUntilChanged()
                    .map {
                        it.onSuccess { recipes ->
                            setSuccessState(recipes)
                        }
                        it.onFailure { error ->
                            setErrorState(error)
                        }
                    }
                    .onStart { setLoadingState() }
                    .launchIn(viewModelScope)

            } catch (e: Exception) {
                Log.e(TAG, "Can not get recipes")
            }
        }
    }

    private fun setLoadingState() {
        val newState = state.value.copyState()
        newState.isLoading = true
        newState.recipes = emptyList()
        newState.error = null
        state.value = newState
    }

    private fun setSuccessState(recipes: List<Recipe>) {
        val newState = state.value.copyState()
        newState.isLoading = false
        newState.recipes = recipes
        newState.error = null
        state.value = newState
    }

    private fun setErrorState(error: String) {
        val newState = state.value.copyState()
        newState.isLoading = false
        newState.recipes = emptyList()
        newState.error = error
        state.value = newState
    }

    private fun RecipesState.copyState(): RecipesState {
        return RecipesState()
    }

    companion object {
        private const val TAG = "RecipesViewModel"

        val providedFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecipesViewModel(RecipesRemoteRepository()) as T
            }
        }
    }
}
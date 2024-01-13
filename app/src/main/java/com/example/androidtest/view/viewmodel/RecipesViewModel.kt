package com.example.androidtest.view.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.androidtest.view.state.RecipesViewState
import com.exemple.androidTest.core.dispatcher.DispatcherProvider
import com.exemple.androidTest.core.repository.RecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<RecipesViewState>() {

    override val initialViewState = RecipesViewState.Loading

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
                            updateViewState(RecipesViewState.Success(recipes))
                        }
                        it.onFailure { error ->
                            updateViewState(RecipesViewState.Error(error.message))
                        }
                    }
                    .onStart {
                        updateViewState(RecipesViewState.Loading)
                    }
                    .flowOn(dispatcherProvider.io)
                    .launchIn(viewModelScope)

            } catch (e: Exception) {
                e.printStackTrace()
                updateViewState(RecipesViewState.Error("Can not get recipes"))
            }
        }
    }

    companion object {
        private const val TAG = "RecipesViewModel"
    }
}
package com.example.androidtest.view.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.androidtest.view.state.RecipesState
import com.exemple.androidTest.core.connectivity.ConnectionDataState
import com.exemple.androidTest.core.connectivity.ConnectionState
import com.exemple.androidTest.core.model.Recipe
import com.exemple.androidTest.core.repository.RecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository,
    private val connectionDataState: ConnectionDataState
) : BaseViewModel<RecipesState>() {

    override val state: MutableStateFlow<RecipesState> = MutableStateFlow(RecipesState.initialState)

    private var syncJob: Job? = null

    init {
        observeConnectivity()
    }

     fun getRecipes() {

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
        state.value = newState
    }

    private fun setSuccessState(recipes: List<Recipe>) {
        val newState = state.value.copyState()
        newState.isLoading = false
        newState.recipes = recipes
        newState.isConnectivityAvailable = true
        newState.error = null
        state.value = newState
    }

    private fun setErrorState(error: String) {
        val newState = state.value.copyState()
        newState.isLoading = false
        newState.isConnectivityAvailable = true
        newState.recipes = emptyList()
        newState.error = error
        state.value = newState
    }


    private fun changeConnectivityState(isConnected: Boolean) {
        val newState = state.value.copyState()
        newState.isConnectivityAvailable = isConnected
        state.value = newState
    }

    private fun observeConnectivity() {
        connectionDataState.observeIsConnected()
            .distinctUntilChanged()
            .onEach { isConnected ->
                if (isConnected == ConnectionState.Available) {
                    getRecipes()
                    return@onEach
                }
                changeConnectivityState(isConnected == ConnectionState.Available)
            }
            .launchIn(viewModelScope)
    }
    fun retry() {
        if (state.value.isConnectivityAvailable == true) {
            getRecipes()
        }
    }

    companion object {
        private const val TAG = "RecipesViewModel"
    }
}
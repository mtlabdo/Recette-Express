package com.example.androidtest.view.viewmodel

import com.exemple.androidTest.core.connectivity.ConnectionDataState
import com.exemple.androidTest.core.connectivity.ConnectionState
import com.exemple.androidTest.core.model.Recipe
import com.exemple.androidTest.core.repository.DataState
import com.exemple.androidTest.core.repository.RecipesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before


open class RecipesViewModelTest {

    private val repository: RecipesRepository = mockk {
        coEvery {
            getAllRecipes()
        } returns mockk()
    }

    private var connectionDataState: ConnectionDataState = mockk()


    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: RecipesViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `When fetching recipes with connectivity is available`() {
        // Given
        val recipes = listOf(
            Recipe(
                idMeal = "52885",
                strMeal = "ubble & Squeak",
                strMealThumb = "https://www.themealdb.com/images/media/meals/xusqvw1511638311.jpg"
            )
        )

        coEvery { repository.getAllRecipes() } returns MutableStateFlow(DataState.success(recipes))

        coEvery { connectionDataState.observeIsConnected() } returns MutableStateFlow(ConnectionState.Available)

        // When
        viewModel = RecipesViewModel(repository, connectionDataState)
        viewModel.getRecipes()

        dispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(true, viewModel.state.value.isConnectivityAvailable)
        assertEquals(recipes, viewModel.state.value.recipes)
        assertEquals(null, viewModel.state.value.error)
    }


    @Test
    fun `When fetching recipes with response error`() {
        // Given
        val errorMessage = "Error fetching recipes"

        coEvery { repository.getAllRecipes() } returns MutableStateFlow(DataState.Error(errorMessage))

        coEvery { connectionDataState.observeIsConnected() } returns MutableStateFlow(ConnectionState.Unavailable)


        // When
        viewModel = RecipesViewModel(repository, connectionDataState)
        viewModel.getRecipes()

        dispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(emptyList<Recipe>(), viewModel.state.value.recipes)
        assertEquals(errorMessage, viewModel.state.value.error)
    }


    @Test
    fun `When fetching recipes with connectivity is unavailable`() {
        // Given

        coEvery { connectionDataState.observeIsConnected() } returns MutableStateFlow(ConnectionState.Unavailable)

        // When
        viewModel = RecipesViewModel(repository, connectionDataState)
        viewModel.getRecipes()

        dispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.state.value.isConnectivityAvailable)
    }
}


package com.example.androidtest.view.viewmodel

import com.example.androidtest.MainCoroutineRule
import com.exemple.androidTest.core.connectivity.ConnectionDataState
import com.exemple.androidTest.core.connectivity.ConnectionState
import com.exemple.androidTest.core.model.Recipe
import com.exemple.androidTest.core.repository.DataState
import com.exemple.androidTest.core.repository.RecipesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Rule

import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
open class RecipesViewModelTest {

    private val repository: RecipesRepository = mockk {
        coEvery {
            getAllRecipes()
        } returns mockk()
    }

    private var connectionDataState: ConnectionDataState = mockk()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: RecipesViewModel

    @Test
    fun `When fetching recipes with connectivity is available`() = coroutineRule.run {
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

        // Then
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(true, viewModel.state.value.isConnectivityAvailable)
        assertEquals(recipes, viewModel.state.value.recipes)
        assertEquals(null, viewModel.state.value.error)
    }


    @Test
    fun `When fetching recipes with response error`() = coroutineRule.run {
        // Given
        val errorMessage = "Error fetching recipes"

        coEvery { repository.getAllRecipes() } returns MutableStateFlow(DataState.Error(errorMessage))

        coEvery { connectionDataState.observeIsConnected() } returns MutableStateFlow(ConnectionState.Unavailable)

        // When
        viewModel = RecipesViewModel(repository, connectionDataState)
        viewModel.getRecipes()

        // Then
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(emptyList<Recipe>(), viewModel.state.value.recipes)
        assertEquals(errorMessage, viewModel.state.value.error)
    }


    @Test
    fun `When fetching recipes with connectivity is unavailable`() = coroutineRule.run {
        // Given

        coEvery { connectionDataState.observeIsConnected() } returns MutableStateFlow(ConnectionState.Unavailable)

        // When
        viewModel = RecipesViewModel(repository, connectionDataState)
        viewModel.getRecipes()

        // Then
        assertEquals(false, viewModel.state.value.isConnectivityAvailable)
    }
}


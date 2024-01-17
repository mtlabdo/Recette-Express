package com.example.androidtest.view.viewmodel

import com.example.androidtest.TestDispatcherProvider
import com.example.androidtest.view.state.RecipesViewState
import com.exemple.androidTest.core.dispatcher.DispatcherProvider
import com.exemple.androidTest.core.model.Recipe
import com.exemple.androidTest.core.repository.DataState
import com.exemple.androidTest.core.repository.ErrorHolder
import com.exemple.androidTest.core.repository.RecipesRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before


@OptIn(ExperimentalCoroutinesApi::class)
open class RecipesViewModelTest {

    private val repository: RecipesRepository = mockk()

    private lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var viewModel: RecipesViewModel

    @Before
    fun setup() {
        dispatcherProvider = TestDispatcherProvider()
        Dispatchers.setMain(dispatcherProvider.main)

    }

    @Test
    fun `Fetch Recipes when repository response success, Should set Success View state `() =
        runTest {
            // Given
            val recipes = listOf(
                Recipe(
                    idMeal = "52885",
                    name = "ubble & Squeak",
                    image = "https://www.themealdb.com/images/media/meals/xusqvw1511638311.jpg"
                )
            )

            val lastViewState = MutableStateFlow<RecipesViewState>(RecipesViewState.Loading)

            every { repository.getAllRecipes() } returns MutableStateFlow(DataState.success(recipes))
            viewModel = RecipesViewModel(repository, dispatcherProvider)

            val job = launch(dispatcherProvider.main) {
                viewModel.viewState.collectLatest {
                    lastViewState.emit(it)
                }
            }

            // Then
            verify(exactly = 1) {
                repository.getAllRecipes()
            }
            assertEquals(RecipesViewState.Success(recipes = recipes), lastViewState.value)
            job.cancel()
        }


    @Test
    fun `Fetch Recipes when repository response Error, Should set Error View state `() = runTest {
        val errorMessage = "Error fetching recipes"

        coEvery { repository.getAllRecipes() } returns MutableStateFlow(
            DataState.failure(
                ErrorHolder.Unknown(errorMessage)
            )
        )
        viewModel = RecipesViewModel(repository, dispatcherProvider)

        val lastViewState = MutableStateFlow<RecipesViewState>(RecipesViewState.Loading)

        val job = launch(dispatcherProvider.main) {
            viewModel.viewState.collectLatest {
                lastViewState.emit(it)
            }
        }

        // Then
        assertEquals(RecipesViewState.Error(errorMessage), lastViewState.value)
        job.cancel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}


/*
package com.example.androidtest.view.viewmodel

import com.exemple.androidTest.core.model.RecipeDetail
import com.exemple.androidTest.core.repository.DataState
import com.exemple.androidTest.core.repository.RecipesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class RecipeDetailViewModelTest {

    private val repository: RecipesRepository = mockk {
        coEvery {
            getRecipeById(any())
        } returns MutableStateFlow(DataState.success(mockRecipeDetail))
    }

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)
    }
    @Test
    fun `When fetching recipe detail with success`()  {
        // Given
        val viewModel = RecipeDetailViewModel(repository, "52885")
        // When
        viewModel.getRecipeDetail()
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(mockRecipeDetail, viewModel.state.value.recipeDetail)
        assertEquals(null, viewModel.state.value.error)
    }

    @Test
    fun `When fetching recipe detail with response error`() {
        // Given
        val errorMessage = "Error fetching recipe detail"
        val repository: RecipesRepository = mockk {
            coEvery {
                getRecipeById(any())
            } returns MutableStateFlow(DataState.Error(errorMessage))
        }

        val viewModel = RecipeDetailViewModel(repository, "456")

        // When
        viewModel.getRecipeDetail()
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(null, viewModel.state.value.recipeDetail)
        assertEquals(errorMessage, viewModel.state.value.error)
    }

    companion object {
        private val mockRecipeDetail = RecipeDetail(
            idMeal = "52885",
            strMeal = "Bubble & Squeak",
            strMealThumb = "https://www.example.com/pasta.jpg",
            strCategory = "Pork",
            strArea = "British",
            strInstructions = "Melt the fat in a non-stick pan, allow it to get nice and hot, then add the bacon. As it begins to brown, add the onion and garlic. Next, add the sliced sprouts or cabbage and let it colour slightly. All this will take 5-6 mins.\\r\\nNext, add the potato. Work everything together in the pan and push it down so that the mixture covers the base of the pan – allow the mixture to catch slightly on the base of the pan before turning it over and doing the same again. It’s the bits of potato that catch in the pan that define the term ‘bubble and squeak’, so be brave and let the mixture colour. Cut into wedges and serve.",
            strYoutube = "https://www.youtube.com/watch?v=etbJ9ssgsWY"
        )
    }
}
*/

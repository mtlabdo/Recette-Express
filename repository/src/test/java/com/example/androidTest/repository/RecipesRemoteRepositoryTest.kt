package com.example.androidTest.repository

import com.example.data.androidTest.remote.api.RecipesService
import com.example.data.androidTest.remote.model.Meal
import com.example.data.androidTest.remote.model.MealDetail
import com.example.data.androidTest.remote.model.RecipeDetailResponse
import com.example.data.androidTest.remote.model.RecipesResponse
import com.exemple.androidTest.core.repository.DataState
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first

import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RecipesRemoteRepositoryTest : BehaviorSpec({
    val service: FakeRecipesService = spyk(FakeRecipesService())
    val repository = RecipesRemoteRepository(service)

    Given("Recipes") {
        When("Recipes are retrieved") {
            And("Operation is successful") {
                service.returnSuccessOnGetAllNotes = true
                val response = repository.getAllRecipes().first()

                Then("Recipes should be requested") {
                    coVerify { service.getRecipes("") }
                }

                Then("Valid response should be returned") {
                    val notes = (response as DataState.Success).data
                    notes shouldHaveSize 1
                    notes.first() shouldBe MealDetail(
                        "53065",
                        "Sushi",
                        "Japanese",
                        "TEP 1\\r\\nTO MAKE SUSHI ROLLS: Pat out some rice. Lay a nori sheet on the mat, shiny-side down. Dip your hands in the vinegared water, then pat handfuls of rice on top in a 1cm thick layer, leaving the furthest edge from you clear.\\r\\n\\r\\nSTEP 2\\r\\nSpread over some Japanese mayonnaise. Use a spoon to spread out a thin layer of mayonnaise down the middle of the rice.\\r\\n\\r\\nSTEP 3\\r\\nAdd the filling. Get your child to top the mayonnaise with a line of their favourite fillings – here we’ve used tuna and cucumber.\\r\\n\\r\\nSTEP 4\\r\\nRoll it up. Lift the edge of the mat over the rice, applying a little pressure to keep everything in a tight roll.\\r\\n\\r\\nSTEP 5\\r\\nStick down the sides like a stamp. When you get to the edge without any rice, brush with a little water and continue to roll into a tight roll.\\r\\n\\r\\nSTEP 6\\r\\nWrap in cling film. Remove the mat and roll tightly in cling film before a grown-up cuts the sushi into thick slices, then unravel the cling film.\\r\\n\\r\\nSTEP 7\\r\\nTO MAKE PRESSED SUSHI: Layer over some smoked salmon. Line a loaf tin with cling film, then place a thin layer of smoked salmon inside on top of the cling film.\\r\\n\\r\\nSTEP 8\\r\\nCover with rice and press down. Press about 3cm of rice over the fish, fold the cling film over and press down as much as you can, using another tin if you have one.\\r\\n\\r\\nSTEP 9\\r\\nTip it out like a sandcastle. Turn block of sushi onto a chopping board. Get a grown-up to cut into fingers, then remove the cling film.\\r\\n\\r\\nSTEP 10\\r\\nTO MAKE SUSHI BALLS: Choose your topping. Get a small square of cling film and place a topping, like half a prawn or a small piece of smoked salmon, on it. Use damp hands to roll walnut-sized balls of rice and place on the topping.\\r\\n\\r\\nSTEP 11\\r\\nMake into tight balls. Bring the corners of the cling film together and tighten into balls by twisting it up, then unwrap and serve.",
                        null,
                        "Seafood",
                        "https://www.themealdb.com/images/media/meals/g046bb1663960946.jpg"
                    )
                }

            }

            And("Operation is unsuccessful") {
                service.returnSuccessOnGetAllNotes = false
                val response = repository.getAllRecipes().first()
                Then("Notes should be requested") {
                    coVerify { service.getRecipes("") }
                }
                Then("Error response should be returned") {
                    val message = (response as DataState.Error).message
                    message shouldBe "Failed"
                }
            }
        }
    }
})


class FakeRecipesService : RecipesService {

    var returnSuccessOnGetAllNotes: Boolean = true

    override suspend fun getRecipes(category: String): Response<RecipesResponse?> {
        return fakeRecipesResponse()
    }

    override suspend fun getRecipeDetail(recipeId: String): Response<RecipeDetailResponse?> {
        return fakeRecipeDetailResponse()
    }


    @OptIn(ExperimentalStdlibApi::class)
    private fun fakeRecipesResponse(): Response<RecipesResponse?> {
        return if (returnSuccessOnGetAllNotes) {
            Response.success(
                RecipesResponse(
                    meals = listOf(
                        Meal(
                            "53065",
                            "Sushi",
                            "Japanese",
                            "TEP 1\\r\\nTO MAKE SUSHI ROLLS: Pat out some rice. Lay a nori sheet on the mat, shiny-side down. Dip your hands in the vinegared water, then pat handfuls of rice on top in a 1cm thick layer, leaving the furthest edge from you clear.\\r\\n\\r\\nSTEP 2\\r\\nSpread over some Japanese mayonnaise. Use a spoon to spread out a thin layer of mayonnaise down the middle of the rice.\\r\\n\\r\\nSTEP 3\\r\\nAdd the filling. Get your child to top the mayonnaise with a line of their favourite fillings – here we’ve used tuna and cucumber.\\r\\n\\r\\nSTEP 4\\r\\nRoll it up. Lift the edge of the mat over the rice, applying a little pressure to keep everything in a tight roll.\\r\\n\\r\\nSTEP 5\\r\\nStick down the sides like a stamp. When you get to the edge without any rice, brush with a little water and continue to roll into a tight roll.\\r\\n\\r\\nSTEP 6\\r\\nWrap in cling film. Remove the mat and roll tightly in cling film before a grown-up cuts the sushi into thick slices, then unravel the cling film.\\r\\n\\r\\nSTEP 7\\r\\nTO MAKE PRESSED SUSHI: Layer over some smoked salmon. Line a loaf tin with cling film, then place a thin layer of smoked salmon inside on top of the cling film.\\r\\n\\r\\nSTEP 8\\r\\nCover with rice and press down. Press about 3cm of rice over the fish, fold the cling film over and press down as much as you can, using another tin if you have one.\\r\\n\\r\\nSTEP 9\\r\\nTip it out like a sandcastle. Turn block of sushi onto a chopping board. Get a grown-up to cut into fingers, then remove the cling film.\\r\\n\\r\\nSTEP 10\\r\\nTO MAKE SUSHI BALLS: Choose your topping. Get a small square of cling film and place a topping, like half a prawn or a small piece of smoked salmon, on it. Use damp hands to roll walnut-sized balls of rice and place on the topping.\\r\\n\\r\\nSTEP 11\\r\\nMake into tight balls. Bring the corners of the cling film together and tighten into balls by twisting it up, then unwrap and serve.",
                            null,
                            "Seafood",
                            "https://www.themealdb.com/images/media/meals/g046bb1663960946.jpg"
                        )
                    )
                )
            )
        } else {
            Response.error(400, null)
        }
    }


    @OptIn(ExperimentalStdlibApi::class)
    private fun fakeRecipeDetailResponse(): Response<RecipeDetailResponse?> {
        return if (returnSuccessOnGetAllNotes) {
            Response.success(
                RecipeDetailResponse(
                    meals = listOf(
                        MealDetail(
                            "53065",
                            "Sushi",
                            "Japanese",
                            "TEP 1\\r\\nTO MAKE SUSHI ROLLS: Pat out some rice. Lay a nori sheet on the mat, shiny-side down. Dip your hands in the vinegared water, then pat handfuls of rice on top in a 1cm thick layer, leaving the furthest edge from you clear.\\r\\n\\r\\nSTEP 2\\r\\nSpread over some Japanese mayonnaise. Use a spoon to spread out a thin layer of mayonnaise down the middle of the rice.\\r\\n\\r\\nSTEP 3\\r\\nAdd the filling. Get your child to top the mayonnaise with a line of their favourite fillings – here we’ve used tuna and cucumber.\\r\\n\\r\\nSTEP 4\\r\\nRoll it up. Lift the edge of the mat over the rice, applying a little pressure to keep everything in a tight roll.\\r\\n\\r\\nSTEP 5\\r\\nStick down the sides like a stamp. When you get to the edge without any rice, brush with a little water and continue to roll into a tight roll.\\r\\n\\r\\nSTEP 6\\r\\nWrap in cling film. Remove the mat and roll tightly in cling film before a grown-up cuts the sushi into thick slices, then unravel the cling film.\\r\\n\\r\\nSTEP 7\\r\\nTO MAKE PRESSED SUSHI: Layer over some smoked salmon. Line a loaf tin with cling film, then place a thin layer of smoked salmon inside on top of the cling film.\\r\\n\\r\\nSTEP 8\\r\\nCover with rice and press down. Press about 3cm of rice over the fish, fold the cling film over and press down as much as you can, using another tin if you have one.\\r\\n\\r\\nSTEP 9\\r\\nTip it out like a sandcastle. Turn block of sushi onto a chopping board. Get a grown-up to cut into fingers, then remove the cling film.\\r\\n\\r\\nSTEP 10\\r\\nTO MAKE SUSHI BALLS: Choose your topping. Get a small square of cling film and place a topping, like half a prawn or a small piece of smoked salmon, on it. Use damp hands to roll walnut-sized balls of rice and place on the topping.\\r\\n\\r\\nSTEP 11\\r\\nMake into tight balls. Bring the corners of the cling film together and tighten into balls by twisting it up, then unwrap and serve.",
                            null,
                            "Seafood",
                            "https://www.themealdb.com/images/media/meals/g046bb1663960946.jpg"
                        )
                    )
                )
            )
        } else {
            Response.error(400, null)
        }
    }
}
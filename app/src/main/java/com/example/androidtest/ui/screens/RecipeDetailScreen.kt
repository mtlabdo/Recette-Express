package com.example.androidtest.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidtest.R
import com.example.androidtest.ui.widget.CardWithTextView
import com.example.androidtest.ui.widget.LoadingIndicator
import com.example.androidtest.ui.widget.ToolbarRecipes
import com.example.androidtest.utils.openUrl
import com.example.androidtest.view.state.RecipeDetailViewState
import com.example.androidtest.view.viewmodel.RecipeDetailViewModel
import com.exemple.androidTest.core.dispatcher.DispatcherProvider
import com.exemple.androidTest.core.model.RecipeDetail


@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel,
    coroutineDispatcher: DispatcherProvider,
    onNavigateUp: () -> Unit
) {

    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            ToolbarRecipes(
                stringResource(id = R.string.toolbar_detail),
                withBack = true,
                onBackClick = onNavigateUp
            )

            val viewState = viewModel.viewState.collectAsState(
                initial = RecipeDetailViewState.Loading,
                coroutineDispatcher.main
            )

            val viewStateValue = viewState.value

            val lastRecipeDetailState = remember {
                mutableStateOf<RecipeDetailViewState.Success?>(null)
            }

            if (viewStateValue is RecipeDetailViewState.Success) {
                lastRecipeDetailState.value = viewStateValue
            }

            when (viewStateValue) {
                is RecipeDetailViewState.Loading -> {
                    LoadingIndicator()
                }

                is RecipeDetailViewState.Success -> {
                    val detailState = lastRecipeDetailState.value
                    RecipeDetails(
                        detailState?.recipeDetail,
                        LocalContext.current
                    )
                }

                is RecipeDetailViewState.Error -> {
                    ErrorText(viewStateValue.error)
                }
            }
        }
    }
}


@Composable
fun ErrorText(error: String) {
    Text(
        text = "Erreur: $error",
        style = LocalTextStyle.current.copy(color = Color.Red),
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}

@Composable
fun RecipeDetails(recipeDetail: RecipeDetail?, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        RecipeDetailItem(
            stringResource(id = R.string.recipe_name),
            recipeDetail?.strMeal ?: "Non disponible"
        )
        Spacer(modifier = Modifier.height(16.dp))

        recipeDetail?.strMealThumb?.let { imageUrl ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(android.R.drawable.ic_dialog_info),
                contentDescription = stringResource(R.string.image_content_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = MaterialTheme.shapes.medium),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        CardWithTextView(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.show_video),
            icon = android.R.drawable.ic_media_play,
            onClick = {
                recipeDetail?.strYoutube?.let {
                    openUrl(context, it)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
        RecipeDetailItem(
            stringResource(id = R.string.recipe_category),
            recipeDetail?.strCategory ?: stringResource(id = R.string.not_available)
        )
        Spacer(modifier = Modifier.height(8.dp))
        RecipeDetailItem(
            stringResource(id = R.string.recipe_region),
            recipeDetail?.strArea ?: stringResource(id = R.string.not_available)
        )
        Spacer(modifier = Modifier.height(8.dp))
        RecipeDetailItem(
            stringResource(id = R.string.recipe_instructions),
            recipeDetail?.strInstructions ?: stringResource(id = R.string.not_available)
        )
    }
}


@Composable
fun RecipeDetailItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = LocalTextStyle.current.copy(fontSize = 16.sp),
        )
    }
}


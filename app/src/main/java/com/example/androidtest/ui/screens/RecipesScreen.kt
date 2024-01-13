package com.example.androidtest.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidtest.R
import com.example.androidtest.ui.widget.LoadingIndicator
import com.example.androidtest.ui.widget.ToolbarRecipes
import com.example.androidtest.view.state.RecipesViewState
import com.example.androidtest.view.viewmodel.RecipesViewModel
import com.exemple.androidTest.core.dispatcher.DispatcherProvider
import com.exemple.androidTest.core.model.Recipe

@Composable
fun RecipesScreen(
    viewModel: RecipesViewModel,
    coroutineDispatcher: DispatcherProvider,
    onNavigateToRecipeDetail: (String) -> Unit,
) {

    Column {

        ToolbarRecipes(stringResource(id = R.string.toolbar_recipes))


        val viewState = viewModel.viewState.collectAsState(
            RecipesViewState.Loading,
            coroutineDispatcher.main
        )

        val lastRecipesState = remember { mutableStateOf<RecipesViewState.Success?>(null) }
        val viewStateValue = viewState.value

        if (viewStateValue is RecipesViewState.Success) {
            lastRecipesState.value = viewStateValue
        }

        when (viewStateValue) {
            is RecipesViewState.Loading -> {
                LoadingIndicator()
            }

            is RecipesViewState.Success -> {
                val recipesState = lastRecipesState.value
                RecipesList(recipesState?.recipes ?: emptyList(), onNavigateToRecipeDetail)
            }

            is RecipesViewState.Error -> {
                ErrorMessage(viewStateValue.error)
            }

            else -> {}
        }
    }
}

@Composable
fun RecipesList(recipes: List<Recipe>, onClick: (String) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 4.dp),
    ) {
        items(
            items = recipes,
            key = { it.idMeal }
        ) { recipe ->
            RecipeCard(
                title = recipe.name ?: "",
                recipeThumb = recipe.image,
                onRecipeClick = { onClick(recipe.idMeal) }
            )
        }
    }
}

@Composable
fun RecipeCard(title: String, recipeThumb: String?, onRecipeClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onRecipeClick() },
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RecipeImage(recipeThumb)
            Spacer(modifier = Modifier.width(16.dp))
            RecipeDetails(title)
        }
    }
}

@Composable
fun RecipeImage(recipeThumb: String?) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(recipeThumb)
            .crossfade(true)
            .build(),
        placeholder = painterResource(android.R.drawable.ic_dialog_info),
        contentDescription = stringResource(R.string.image_content_description),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(CircleShape)
            .size(64.dp)
    )
}

@Composable
fun RecipeDetails(title: String) {
    Column {
        Text(
            text = title,
            lineHeight = 24.sp,
            maxLines = 2,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ErrorMessage(error: String) {
    Text(
        text = error,
        color = Color.Red,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

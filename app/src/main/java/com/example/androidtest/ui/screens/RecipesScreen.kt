package com.example.androidtest.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
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
import com.example.androidtest.utils.collectState
import com.example.androidtest.utils.collection.ComposeImmutableList
import com.example.androidtest.utils.collection.rememberComposeImmutableList
import com.example.androidtest.view.viewmodel.RecipesViewModel
import com.exemple.androidTest.core.model.Recipe

@Composable
fun RecipesScreen(
    viewModel: RecipesViewModel,
    onNavigateToRecipeDetail: (String) -> Unit,
) {
    val state by viewModel.collectState()
    val recipes by rememberComposeImmutableList { state.recipes }

    Column {
        ToolbarRecipes(stringResource(id = R.string.toolbar_recipes))

        RecipesContent(
            isLoading = state.isLoading,
            recipes = recipes,
            isConnectivityAvailable = state.isConnectivityAvailable,
            error = state.error,
            onNavigateToRecipeDetail = onNavigateToRecipeDetail
        )
    }
}

@Composable
fun RecipesContent(
    isLoading: Boolean,
    recipes: ComposeImmutableList<Recipe>,
    isConnectivityAvailable: Boolean?,
    error: String? = null,
    onNavigateToRecipeDetail: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            RecipesList(recipes, onNavigateToRecipeDetail)
        }

        when {
            isLoading -> LoadingIndicator()
            isConnectivityAvailable == false -> NoInternetConnectionMessage()
            error != null -> ErrorMessage(error)
        }
    }
}

@Composable
fun RecipesList(recipes: ComposeImmutableList<Recipe>, onClick: (String) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 4.dp),
    ) {
        items(
            items = recipes,
            key = { it.idMeal }
        ) { recipe ->
            RecipeCard(
                title = recipe.strMeal ?: "",
                recipeThumb = recipe.strMealThumb,
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
fun NoInternetConnectionMessage() {
    Text(
        text = "No Internet Connection",
        color = Color.Red,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun ErrorMessage(error: String) {
    Text(
        text = "Error: $error",
        color = Color.Red,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )

}

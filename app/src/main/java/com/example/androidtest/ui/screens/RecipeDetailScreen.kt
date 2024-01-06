package com.example.androidtest.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.androidtest.utils.collectState
import com.example.androidtest.utils.openUrl
import com.example.androidtest.view.viewmodel.RecipeDetailViewModel
import com.exemple.androidTest.core.model.RecipeDetail


@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel,
    onNavigateUp: () -> Unit
) {
    val state by viewModel.collectState()

    Column {
        ToolbarRecipes(
            stringResource(id = R.string.toolbar_detail),
            withBack = true,
            onBackClick = onNavigateUp
        )

        RecipeDetailContent(
            recipeDetail = state.recipeDetail,
            isLoading = state.isLoading,
            isConnectivityAvailable = false,
            error = state.error,
            context = LocalContext.current
        )
    }
}

@Composable
fun RecipeDetailContent(
    recipeDetail: RecipeDetail?,
    isLoading: Boolean,
    isConnectivityAvailable: Boolean?,
    error: String? = null,
    context: Context
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp)
    ) {
        item {
            if (isLoading) {
                LoadingIndicator()
            } else {
                when {
                    error != null -> ErrorText(error)
                    else -> RecipeDetails(recipeDetail, context)
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
        RecipeDetailItem("Nom de la recette", recipeDetail?.strMeal ?: "Non disponible")
        Spacer(modifier = Modifier.height(16.dp))

        recipeDetail?.strMealThumb?.let { imageUrl ->
            // Afficher la photo
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
            label = "Voir la vidéo",
            icon = android.R.drawable.ic_media_play, // Replace with your actual icon
            onClick = {
                recipeDetail?.strYoutube?.let {
                    openUrl(context, it)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
        RecipeDetailItem("Catégorie", recipeDetail?.strCategory ?: "Non disponible")
        Spacer(modifier = Modifier.height(8.dp))
        RecipeDetailItem("Région", recipeDetail?.strArea ?: "Non disponible")
        Spacer(modifier = Modifier.height(8.dp))
        RecipeDetailItem("Instructions", recipeDetail?.strInstructions ?: "Non disponibles")
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


package com.example.androidtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.androidtest.navigation.AppNavigation
import com.example.androidtest.ui.theme.AndroidTestTheme
import com.example.androidtest.view.viewmodel.RecipeDetailViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipesAppTheme()
        }
    }

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun recipeDetailViewModelFactory(): RecipeDetailViewModel.Factory
    }

    @Composable
    fun RecipesAppTheme() {
        AndroidTestTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AppNavigation()
            }
        }
    }
}
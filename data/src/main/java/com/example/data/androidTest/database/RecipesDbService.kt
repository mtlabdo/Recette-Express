package com.example.data.androidTest.database

import com.example.data.androidTest.database.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

interface RecipesDbService {

    fun getAllRecipes(): Flow<List<RecipeEntity>>

    fun deleteAllAndInsert(recipes: List<RecipeEntity>)

}
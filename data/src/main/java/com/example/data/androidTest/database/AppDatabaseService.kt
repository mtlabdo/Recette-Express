package com.example.data.androidTest.database

import com.example.data.androidTest.database.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

class AppDatabaseService(
    private val recipesDatabase: RecipesDatabase
) : RecipesDbService {
    override fun getAllRecipes(): Flow<List<RecipeEntity>> {
        return recipesDatabase.getRecipeDao().getAllRecipes()
    }

    override fun deleteAllAndInsert(recipes: List<RecipeEntity>) {
        recipesDatabase.getRecipeDao().deleteAllAndInsertAll(recipes)
    }
}
package com.example.data.androidTest.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.androidTest.database.dao.RecipeDao
import com.example.data.androidTest.database.entity.RecipeEntity


@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao

    companion object {
        fun buildDatabase(application: Application, dbName: String) =
            Room.databaseBuilder(application, RecipesDatabase::class.java, dbName).build()
    }
}
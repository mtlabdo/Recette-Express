package com.example.androidtest.di.module

import android.app.Application
import android.content.Context
import com.example.androidtest.di.DbName
import com.example.data.androidTest.database.AppDatabaseService
import com.example.data.androidTest.database.RecipesDatabase
import com.example.data.androidTest.database.RecipesDbService
import com.example.data.androidTest.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRecipesDatabase(
        application: Application,
        @ApplicationContext appContext: Context,
        @DbName dbName: String
    ): RecipesDatabase = RecipesDatabase.buildDatabase(application, dbName)

    @DbName
    @Provides
    fun provideDbName() = Constant.ROOM_DATABASE_NAME

    @Provides
    @Singleton
    fun provideRecipesDatabaseService(recipesDatabase: RecipesDatabase): RecipesDbService =
        AppDatabaseService(recipesDatabase)

}
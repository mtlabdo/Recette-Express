package com.example.androidtest.di

import com.example.data.androidTest.remote.api.RecipesService
import com.example.data.androidTest.remote.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideRecipesService(): RecipesService {
        return RetrofitClient().getRetrofit<RecipesService>()
    }
}
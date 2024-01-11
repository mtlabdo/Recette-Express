package com.example.androidtest.di.module

import com.example.androidtest.di.BaseUrl
import com.example.data.androidTest.remote.Constant
import com.exemple.androidTest.core.dispatcher.DefaultDispatcherProvider
import com.exemple.androidTest.core.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()


    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = Constant.API_BASE_URL
}
package com.example.androidtest.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.androidtest.utils.ConnectionDataSourceImpl
import com.exemple.androidTest.core.connectivity.ConnectionDataState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.MainScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    fun providesConnectionState(
        connectivityManager: ConnectivityManager,
    ): ConnectionDataState =
        ConnectionDataSourceImpl(connectivityManager, MainScope())
}
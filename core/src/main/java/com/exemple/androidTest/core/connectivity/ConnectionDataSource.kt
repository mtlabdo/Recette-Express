package com.exemple.androidTest.core.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectionDataState {
    fun observeIsConnected(): Flow<ConnectionState>
}

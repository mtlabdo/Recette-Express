package com.example.androidtest.utils

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.exemple.androidTest.core.connectivity.ConnectionDataState
import com.exemple.androidTest.core.connectivity.ConnectionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ConnectionDataSourceImpl(
    private val connectivityManager: ConnectivityManager,
    private val coroutineScope: CoroutineScope,
) : ConnectionDataState {

    private val networkRequestProvider: () -> NetworkRequest = {
        NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
    }

    private val stateFlow = MutableStateFlow<ConnectionState>(ConnectionState.Unset)

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            emitConnectionUpdate(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            emitConnectionUpdate(
                if (isConnected()) {
                    ConnectionState.Available
                } else {
                    ConnectionState.Unavailable
                }
            )
        }

        private fun isConnected() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            connectivityManager.getNetworkCapabilities(activeNetwork)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            @Suppress("DEPRECATION") val activeNetworkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION") activeNetworkInfo?.isConnected == true
        }
    }

    private fun emitConnectionUpdate(connectionState: ConnectionState) {
        coroutineScope.launch(Dispatchers.IO) {
            stateFlow.emit(connectionState)
        }
    }

    override fun observeIsConnected(): Flow<ConnectionState> {
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (ignore: IllegalArgumentException) {
        }
        connectivityManager.registerNetworkCallback(networkRequestProvider(), networkCallback)

        return stateFlow
    }
}

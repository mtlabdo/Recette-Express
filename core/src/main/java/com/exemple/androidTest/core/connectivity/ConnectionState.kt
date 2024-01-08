
package com.exemple.androidTest.core.connectivity

sealed class ConnectionState {

    object Unset : ConnectionState()

    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}

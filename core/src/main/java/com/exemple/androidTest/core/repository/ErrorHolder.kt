package com.exemple.androidTest.core.repository

sealed class ErrorHolder(override val message: String) : Throwable(message) {

    data class NetworkConnection(override val message: String) : ErrorHolder(message)
    data class InternalServerError(override val message: String) : ErrorHolder(message)
    data class ResourceNorFoundServer(override val message: String) : ErrorHolder(message)
    data class UnAuthorized(override val message: String) : ErrorHolder(message)
    data class DatabaseError(override val message: String) : ErrorHolder(message)
    data class Unknown(override val message: String) : ErrorHolder(message)
}
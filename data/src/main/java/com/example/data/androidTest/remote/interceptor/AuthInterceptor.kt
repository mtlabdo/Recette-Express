package com.example.data.androidTest.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

// TODO A utiliser si l'api le demande

class AuthInterceptor (
) : Interceptor {

    @Synchronized
    override fun intercept(chain: Interceptor.Chain): Response {
        val authRequest = chain.request().newBuilder().apply {
            header("Authorization", "Bearer XXXXX")
        }.build()
        return chain.proceed(authRequest)
    }
}
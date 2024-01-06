package com.example.data.androidTest.remote.retrofit

import com.example.data.androidTest.remote.Constant
import com.exemple.androidTest.core.utils.moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    inline fun <reified T> getRetrofit(
    ): T {
        val baseUrl = Constant.API_BASE_URL
        val okHttpClient = getHttpClient()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

        return retrofit.create(T::class.java)
    }

    fun getHttpClient() =
        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

}
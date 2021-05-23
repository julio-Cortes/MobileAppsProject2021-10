package com.example.miniproject03.Retrofit


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }

    private fun getOkClient(): OkHttpClient {
        return OkHttpClient()
                .newBuilder()
                .addInterceptor(makeLoggingInterceptor(isDebug = true))
                .build()
    }

    fun getRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder().baseUrl("https://kfeeav6eie.execute-api.us-east-1.amazonaws.com/")
                .client(getOkClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit
    }
}
package com.example.miniproject03.Retrofit



import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    fun getRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder().baseUrl("https://kfeeav6eie.execute-api.us-east-1.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();
        return retrofit
    }
}
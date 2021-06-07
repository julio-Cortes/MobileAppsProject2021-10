package com.example.project_01.Reftrofit

import com.example.project_01.Models.Deck
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserRemoteRepository {
    @POST("signup")
    suspend fun signUp(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("login")
    suspend fun logIn(@Body requestBody: RequestBody): Response<ResponseBody>

}
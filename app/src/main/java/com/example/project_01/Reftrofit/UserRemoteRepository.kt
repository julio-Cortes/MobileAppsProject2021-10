package com.example.project_01.Reftrofit

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRemoteRepository {
    @POST("Signup")
    suspend fun signUp(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("Login")
    suspend fun logIn(@Body requestBody: RequestBody): Response<ResponseBody>
}
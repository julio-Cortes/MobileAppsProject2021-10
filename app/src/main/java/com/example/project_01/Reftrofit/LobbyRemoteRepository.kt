package com.example.project_01.Reftrofit

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LobbyRemoteRepository {
    @POST("room")
    fun createRoom(@Header("token") authHeader: String, @Body requestBody: RequestBody): Response<ResponseBody>

    @POST("joinRoom")
    fun joinRoom(@Header("token") authHeader: String, @Body requestBody: RequestBody): Response<ResponseBody>
}
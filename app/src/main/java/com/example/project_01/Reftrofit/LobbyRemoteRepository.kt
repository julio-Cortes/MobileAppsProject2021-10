package com.example.project_01.Reftrofit

import com.example.project_01.Deserializers.LobbyCredentials
import com.example.project_01.Deserializers.LobbyListCredentials
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface LobbyRemoteRepository {
    @POST("rooms")
    suspend fun createRoom(@Header("token") token: String, @Body requestBody: RequestBody): Response<ResponseBody>

    @POST("joinRoom")
    suspend fun joinRoom(@Header("token") token: String, @Body requestBody: RequestBody): Response<ResponseBody>

    @GET("rooms")
    suspend fun getRooms(@Header("token") token: String): Response<ResponseBody>

    @GET("room")
    fun getRoom(@Query("roomName") roomName : String?, @Header("token") token : String) : Call<LobbyCredentials>

    @DELETE("room")
    fun deleteRoom(@Header("token") token : String, @Body requestBody: RequestBody) : Response<ResponseBody>
}
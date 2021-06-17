package com.example.project_01.Reftrofit

import com.example.project_01.Deserializers.CreateLobbyCredentials
import com.example.project_01.Deserializers.LobbyCredentials
import com.example.project_01.Deserializers.LobbyListCredentials
import com.example.project_01.Models.Deck
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface LobbyRemoteRepository {
    @POST("rooms")
    suspend fun createRoom(@Header("token") token: String, @Body lobby: CreateLobbyCredentials): Response<ResponseBody>

    @POST("joinRoom")
    suspend fun joinRoom(@Header("token") token: String, @Body requestBody: RequestBody): Response<ResponseBody>

    @GET("rooms")
    suspend fun getRooms(@Header("token") token: String): Response<ResponseBody>

    @HTTP(method = "DELETE", path = "room", hasBody = true)
    suspend fun deleteRoom(@Header("token") token : String, @Body requestBody: RequestBody) : Response<ResponseBody>


    @GET("getResult/{roomName}")
    suspend fun getResult( @Header ("token") token:String, @Path("roomName") roomName: String?):Response<ResponseBody>

    @POST("vote")
    suspend fun vote(@Header("token") token:String, @Body requestBody: RequestBody) :Response<ResponseBody>

    @GET("rooms/{roomName}")
    suspend fun getRoom(@Path("roomName") roomName : String?, @Header("token") token : String) : Response<ResponseBody>

}
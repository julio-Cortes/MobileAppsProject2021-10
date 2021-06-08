package com.example.project_01.Reftrofit

import com.example.project_01.Models.Deck
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface DecksRemoteRepository {
    @GET("decks")
    suspend fun getDecks(): Response<ResponseBody>
}
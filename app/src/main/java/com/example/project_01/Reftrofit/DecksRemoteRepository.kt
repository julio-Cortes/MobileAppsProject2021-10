package com.example.project_01.Reftrofit

import com.example.project_01.Models.Deck
import retrofit2.Call
import retrofit2.http.GET

interface DecksRemoteRepository {
    @GET("decks")
    fun getDecks(): Call<List<Deck>>
}
package com.example.project_01.Repositories

import android.widget.Toast
import com.example.miniproject03.Retrofit.ServiceBuilder
import com.example.project_01.Models.Deck
import com.example.project_01.Reftrofit.DecksRemoteRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DeckRepository() {
    private val service: DecksRemoteRepository
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    init{
        service = ServiceBuilder.getRetrofit().create(DecksRemoteRepository::class.java)
    }
    suspend fun getDecks(): String? {
        val jsonObject = JSONObject()
        val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val call = service.getDecks()
        return call.body()?.string()
    }

}
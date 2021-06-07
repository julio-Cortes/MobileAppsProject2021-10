package com.example.project_01.Repositories

import android.app.Application
import android.widget.Toast
import com.example.miniproject03.Retrofit.ServiceBuilder
import com.example.project_01.Models.Deck
import com.example.project_01.Reftrofit.DecksRemoteRepository
import com.example.project_01.Reftrofit.UserRemoteRepository
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DeckRepository() {
    private val service: DecksRemoteRepository
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    var list_deck = mutableListOf<Deck>()

    init{
        service = ServiceBuilder.getRetrofit().create(DecksRemoteRepository::class.java)
    }
    fun getDecks() : MutableList<Deck> {
        val call = service.getDecks()
        call.enqueue(object : Callback<List<Deck>> {
            override fun onResponse(
                call: Call<List<Deck>>,
                response: Response<List<Deck>>
            ) {
                val body = response.body()
                if (body != null) {
                    //executor.execute {
                        body.forEach {
                            list_deck.add(it)
                        }
                    //}
                }
            }
            override fun onFailure(call: Call<List<Deck>>, t: Throwable) {
                println(t.message)
            }
        })
        return list_deck
    }

}
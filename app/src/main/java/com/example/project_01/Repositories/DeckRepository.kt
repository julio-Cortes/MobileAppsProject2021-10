package com.example.project_01.Repositories

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.miniproject03.Retrofit.ServiceBuilder
import com.example.project_01.Database.Database
import com.example.project_01.Database.DeckDao
import com.example.project_01.Database.LobbyDao
import com.example.project_01.Deserializers.DecksCredentials
import com.example.project_01.Models.Deck
import com.example.project_01.Models.Lobby
import com.example.project_01.Reftrofit.DecksRemoteRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DeckRepository(application: Application, database: Database ) {
    val app = application
    private val service: DecksRemoteRepository
    private val database = database
    lateinit var decks : List<Deck>
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private val deckDao: DeckDao

    init{
        service = ServiceBuilder.getRetrofit().create(DecksRemoteRepository::class.java)
        deckDao = database.DeckDao()
        executor.execute{
            decks = deckDao.getAll()
        }



    }
    suspend fun GetDecksFromApi(): Array<DecksCredentials> {
        val call = service.getDecks()
        val response = call.body()?.string()
        val gson = Gson()
        val Decks = gson.fromJson(response, Array<DecksCredentials>::class.java)
        executor.execute{
            Decks.forEach {
                it.cards.toMutableList().addAll(listOf("?","☕"))
                deckDao.insert(Deck(it.name, it.cards.toString()))
            }
        }
        return Decks
    }
    fun GetDecksFromDatabase():ArrayList<DecksCredentials> {
        val valores = mutableListOf<String>()
        val deckk = mutableListOf<DecksCredentials>()
        decks.forEach {
            it.cards.forEach {
                valores.add(it.toString())
            }
            valores.remove("[")
            valores.remove("]")
            valores.remove('"')
            deckk.add(DecksCredentials(it.name_deck,valores))
            valores.clear()
        }
        return deckk as ArrayList<DecksCredentials>
    }

}
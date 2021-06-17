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

class DeckRepository(application: Application,decksRemoteRepository: DecksRemoteRepository, deckDao: DeckDao ) {
    val app = application
    private val service = decksRemoteRepository
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private val deckDao = deckDao
    lateinit var decks : List<Deck>
    var deck_credentials = mutableListOf<DecksCredentials>()

    init{
        executor.execute{
            decks = deckDao.getAll()
        }
    }
    suspend fun GetDecksFromApi(): Array<DecksCredentials> {
        val call = service.getDecks()
        val response = call.body()?.string()
        val gson = Gson()
        val Decks = gson.fromJson(response, Array<DecksCredentials>::class.java)

        Decks.forEach {
            deckDao.insert(Deck(it.name, it.cards.toString()))
        }
        return Decks
    }
    fun GetDecksFromDatabase():ArrayList<DecksCredentials> {
        val valores = mutableListOf<String>()
        val deckk = mutableListOf<DecksCredentials>()
        deck_credentials.clear()
        decks.forEach {
            it.cards?.forEach {
                valores.add(it.toString())
            }
            valores.remove("[")
            valores.remove("]")
            valores.remove('"')
            deckk.add(DecksCredentials(it.name_deck,it.toDeckCredentials().cards))

            deck_credentials.add(DecksCredentials(it.name_deck,it.toDeckCredentials().cards))
            valores.clear()
        }
        return deckk as ArrayList<DecksCredentials>
    }


}
package com.example.project_01.ViewModels

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_01.Deserializers.UserCredentials
import com.example.project_01.MainActivity
import com.example.project_01.Models.Deck
import com.example.project_01.Navigator.Navigator
import com.example.project_01.Repositories.DeckRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class CardsViewModel(application: Application):  AndroidViewModel(application){
    val app = application
    lateinit var navigator: Navigator
    val deckRepository = DeckRepository()

    fun changeDeck(position: Int) {

        val sharedPreferences = app?.getSharedPreferences("current_deck", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.apply{
            putInt("deck",position)
        }?.apply()

        //this.currentDeck = Decks[position]

    }


    lateinit var deckSelector: Spinner

   lateinit var currentDeck:Deck
   lateinit var Decks:List<Deck>
    lateinit var name:String
    lateinit var cards:List<String>

    init{

        //currentDeck = Decks[0]
    }

        //listOf<Deck>(
        //    Deck("Standard", mutableListOf("0","1/2","1","2","3","5","8","13","20","40","100","∞")),
        //    Deck("T-Shirt", mutableListOf("XS","S","M","L","XL","XXL")),
        //    Deck("Fibonacci",mutableListOf("0","1","2","3","5","8","13","21","34","55","89","144","∞")),
        //    Deck("Hours", mutableListOf("0","1","2","3","4","6","8","12","16","24","32"))
    //)

    fun setNavigator(activity: MainActivity){
        navigator = Navigator(activity)
    }

    fun CardFragmentToSelectedCard(view: View, num:String){
        navigator.goToCardFragmentToSelectedCard(view, num)

    }

    fun Load() {
        viewModelScope.launch {
            val response = deckRepository.getDecks()!!
            Toast.makeText(app.applicationContext,response, Toast.LENGTH_LONG).show()
            val gson = Gson()

            val result = gson.fromJson(response, Decks::class.java)
            Decks = result as List<Deck>

            val sharedPreferences = app?.getSharedPreferences("current_deck", Context.MODE_PRIVATE)
            currentDeck=Decks[sharedPreferences.getInt("deck",0)]
        }

    }
}

package com.example.project_01.ViewModels

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_01.Deserializers.DecksCredentials
import com.example.project_01.Deserializers.UserCredentials
import com.example.project_01.MainActivity
import com.example.project_01.Models.Deck
import com.example.project_01.Navigator.Navigator
import com.example.project_01.Repositories.DeckRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class CardsViewModel(application: Application, deckRepository: DeckRepository, navigator: Navigator):  AndroidViewModel(application){
    val app = application
    val navigator = navigator
    val deckRepository = deckRepository

    fun changeDeck(position: Int) {

        val sharedPreferences = app?.getSharedPreferences("current_deck", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.apply{
            putInt("deck",position)
        }?.apply()

        this.currentDeck = Decks[position]

    }
    lateinit var deckSelector: Spinner
    lateinit var currentDeck:DecksCredentials
    lateinit var Decks:List<DecksCredentials>


    init {
        val sharedPreferences = app?.getSharedPreferences("current_deck", Context.MODE_PRIVATE)

        viewModelScope.launch {
            Decks = deckRepository.getDecks()!!.toList()
            currentDeck = Decks[sharedPreferences.getInt("deck", 0)]
        }
        Decks = deckRepository.getDecksBase()//NECESITAMOS QUE LA BASE ESTE LLENA ANTES DE PODER DESCOMENTAR LO SIGUIENTE
        //currentDeck = Decks[sharedPreferences.getInt("deck", 0)] //NO PODEMOS USARLO YA QUE NO SE ALCANZA A LLENAR LA BASE ANTES DE QUE SE PONGA A LEERLA
        currentDeck = DecksCredentials("Standard", listOf("0","1/2","1","2","3","5","8","13","20","40","100","∞"))
    }

        //listOf<Deck>(
        //    Deck("Standard", mutableListOf("0","1/2","1","2","3","5","8","13","20","40","100","∞")),
        //    Deck("T-Shirt", mutableListOf("XS","S","M","L","XL","XXL")),
        //    Deck("Fibonacci",mutableListOf("0","1","2","3","5","8","13","21","34","55","89","144","∞")),
        //    Deck("Hours", mutableListOf("0","1","2","3","4","6","8","12","16","24","32"))
    //)


    fun CardFragmentToSelectedCard(view: View, num:String){
        navigator.goToCardFragmentToSelectedCard(view, num)

    }

}

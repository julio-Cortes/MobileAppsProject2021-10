package com.example.project_01.ViewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Spinner
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_01.Deserializers.DecksCredentials
import com.example.project_01.Models.Deck
import com.example.project_01.Navigator.Navigator
import com.example.project_01.Repositories.DeckRepository
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
    var Decks = listOf<DecksCredentials>()


    init{
        val cm = app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val sharedPreferences = app?.getSharedPreferences("current_deck", Context.MODE_PRIVATE)
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            viewModelScope.launch {
                Decks = deckRepository.GetDecksFromApi()!!.toList()
            }
        }
        else{
            viewModelScope.launch {
                Decks = deckRepository.GetDecksFromDatabase()//NECESITAMOS QUE LA BASE ESTE LLENA ANTES DE PODER DESCOMENTAR LO SIGUIENTE


            }
        }

        if (Decks.size>0){
            currentDeck = Decks[sharedPreferences.getInt("deck", 0)] //NO PODEMOS USARLO YA QUE NO SE ALCANZA A LLENAR LA BASE ANTES DE QUE SE PONGA A LEERLA
        }
    }
    fun load(){

        print("hello")
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

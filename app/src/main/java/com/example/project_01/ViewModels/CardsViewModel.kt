package com.example.project_01.ViewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Spinner
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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

        this.currentDeck.postValue(deckRepository.deck_credentials[position])

    }
    lateinit var deckSelector: Spinner
    var currentDeck: MutableLiveData<DecksCredentials> = MutableLiveData()
    var Decks: MutableLiveData<MutableList<DecksCredentials>> = MutableLiveData()

    init{
        val cm = app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val sharedPreferences = app?.getSharedPreferences("current_deck", Context.MODE_PRIVATE)
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            viewModelScope.launch {
                Decks.postValue(deckRepository.GetDecksFromApi()!!.toMutableList())


            }
        }
        else{
            viewModelScope.launch {
                //Decks = deckRepository.GetDecksFromDatabase()
                //currentDeck.postValue(Decks[sharedPreferences.getInt("deck", 0)])
            }
        }

        if (deckRepository.deck_credentials.size>0){
            val pos = sharedPreferences.getInt("deck", 0)
            currentDeck.postValue(deckRepository.deck_credentials[pos])
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

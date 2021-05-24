package com.example.project_01.ViewModels

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.example.project_01.MainActivity
import com.example.project_01.Models.Deck
import com.example.project_01.Navigator.Navigator
import com.example.project_01.Views.Fragments.CardsFragmentDirections

class CardsViewModel(application: Application):  AndroidViewModel(application){
    val app = application
    lateinit var navigator: Navigator

    fun changeDeck(position: Int) {

        val sharedPreferences = app?.getSharedPreferences("current_deck", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.apply{
            putInt("deck",position)
        }?.apply()

        this.currentDeck = Decks[position]

    }


    lateinit var deckSelector: Spinner

    var currentDeck:Deck

    val Decks = listOf<Deck>(
            Deck("Standard", mutableListOf("0","1/2","1","2","3","5","8","13","20","40","100","∞")),
            Deck("T-Shirt", mutableListOf("XS","S","M","L","XL","XXL")),
            Deck("Fibonacci",mutableListOf("0","1","2","3","5","8","13","21","34","55","89","144","∞")),
            Deck("Hours", mutableListOf("0","1","2","3","4","6","8","12","16","24","32"))
    )

    init {
        Decks.forEach{it.cards.addAll(listOf("?","☕"))}
        val sharedPreferences = app?.getSharedPreferences("current_deck", Context.MODE_PRIVATE)

        currentDeck=Decks[sharedPreferences.getInt("deck",0)]

    }
    fun setNavigator(activity: MainActivity){
        navigator = Navigator(activity)
    }

    fun CardFragmentToSelectedCard(view: View, num:String){
        navigator.goToCardFragmentToSelectedCard(view, num)

    }
}

package com.example.project_01.ViewModels

import android.app.Application
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.project_01.Models.Deck

class CardsViewModel(application: Application):  AndroidViewModel(application){

    fun changeDeck(position: Int) {
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

        currentDeck=Decks[0]

    }

}

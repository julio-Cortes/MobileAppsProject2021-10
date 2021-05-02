package com.example.project_01.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application):  AndroidViewModel(application){
    var currentDeck =  mutableListOf<String>()
    val myDeck = MutableLiveData<MutableList<String>>()
    init {
        currentDeck.add("1")
        currentDeck.add("2")
        currentDeck.add("3")
        currentDeck.add("4")
        currentDeck.add("5")
        currentDeck.add("6")
        currentDeck.add("7")
        currentDeck.add("8")
        myDeck.postValue(currentDeck)

    }

}

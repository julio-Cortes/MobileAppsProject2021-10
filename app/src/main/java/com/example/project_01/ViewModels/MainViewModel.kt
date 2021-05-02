package com.example.project_01.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application):  AndroidViewModel(application){
    lateinit var currentDeck: List<Integer>
}

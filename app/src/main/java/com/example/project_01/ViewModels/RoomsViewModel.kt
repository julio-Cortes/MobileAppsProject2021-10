package com.example.project_01.ViewModels

import android.app.Application
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.project_01.Models.Room
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


class RoomsViewModel(application: Application) : AndroidViewModel(application) {


    val caseList = mutableListOf<Room>()
    val myCases =  MutableLiveData<MutableList<Room>>()

    fun addCase(room:Room) {

        caseList.add(room)
        myCases.postValue(caseList)

    }

    fun deleteCase(num: Int){
        caseList.removeAt(num)
    }


}

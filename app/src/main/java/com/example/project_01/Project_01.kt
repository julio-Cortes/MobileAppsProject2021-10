package com.example.project_01

import android.app.Application
import com.example.project_01.Modules.appModule
import com.example.project_01.Modules.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Project_01 :Application(){
    override fun onCreate(){{}
        super.onCreate()
        startKoin{
            androidContext(this@Project_01)
            modules(listOf(appModule, networkModule ))
        }
    }
}
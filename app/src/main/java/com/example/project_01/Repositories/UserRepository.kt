package com.example.project_01.Repositories

import android.app.Application
import androidx.room.Room
import com.example.miniproject03.Retrofit.ServiceBuilder
import com.example.project_01.Database.Database
import com.example.project_01.Models.User
import com.example.project_01.Reftrofit.UserRemoteRepository
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {

    private val service: UserRemoteRepository
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        service = ServiceBuilder.getRetrofit().create(UserRemoteRepository::class.java)


    }
}
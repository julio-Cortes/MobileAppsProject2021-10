package com.example.project_01.Modules

import com.example.miniproject03.Retrofit.ServiceBuilder
import com.example.project_01.Reftrofit.DecksRemoteRepository
import com.example.project_01.Reftrofit.LobbyRemoteRepository
import org.koin.dsl.module

val networkModule = module {
    single{ServiceBuilder.getRetrofit()}
    single{ServiceBuilder.getRetrofit().create(LobbyRemoteRepository::class.java)}
    single{}
}
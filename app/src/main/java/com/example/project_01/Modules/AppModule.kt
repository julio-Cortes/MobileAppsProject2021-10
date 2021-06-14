package com.example.project_01.Modules

import androidx.room.Room
import com.example.project_01.Database.Database
import com.example.project_01.MainActivity
import com.example.project_01.Models.Lobby
import com.example.project_01.Navigator.Navigator
import com.example.project_01.Repositories.DeckRepository
import com.example.project_01.Repositories.GeneralRepository
import com.example.project_01.Repositories.RoomRepository
import com.example.project_01.Repositories.UserRepository
import com.example.project_01.ViewModels.CardsViewModel
import com.example.project_01.ViewModels.LobbyViewModel
import com.example.project_01.ViewModels.UsersViewModel
import com.example.project_01.Views.Adapters.CardAdapter
import com.example.project_01.Views.Adapters.LobbyAdapter
import com.example.project_01.Views.Fragments.CardsFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single{ Room.databaseBuilder(get(), Database::class.java, "planning_poker-db").fallbackToDestructiveMigration().build()}
    single { get<Database>().RoomDao() }
    single{ get<Database>().DeckDao() }
    single { Navigator(get()) }
    single { DeckRepository(get(),get()) }
    single { GeneralRepository(get()) }
    single { RoomRepository(get(),get(),get(),get()) }
    single { UserRepository(get(),get()) }
    single {MainActivity()}
    viewModel { CardsViewModel(get(),get(),get()) }
    viewModel { LobbyViewModel(get(),get(),get()) }
    viewModel { UsersViewModel(get(),get()) }


}
package com.example.project_01.Modules

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.project_01.Database.Database
import com.example.project_01.MainActivity
import com.example.project_01.Navigator.Navigator
import com.example.project_01.Repositories.DeckRepository
import com.example.project_01.Repositories.LobbyRepository
import com.example.project_01.Repositories.UserRepository
import com.example.project_01.ViewModels.CardsViewModel
import com.example.project_01.ViewModels.LobbyViewModel
import com.example.project_01.ViewModels.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.N)
val appModule = module {
    single{ Room.databaseBuilder(get(), Database::class.java, "planning_poker-db").fallbackToDestructiveMigration().build()}
    single { get<Database>().LobbyDao() }
    single{ get<Database>().DeckDao() }
    single { Navigator(get()) }
    single { DeckRepository(get(),get(),get()) }
    single { LobbyRepository(get(),get(),get()) }
    single { UserRepository(get(),get(),get()) }
    single {MainActivity()}
    viewModel { CardsViewModel(get(),get(),get()) }
    viewModel { LobbyViewModel(get(),get(),get()) }
    viewModel { UsersViewModel(get(),get(),get()) }


}
package com.example.project_01.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.project_01.Deserializers.LobbyCredentials
import com.example.project_01.Models.Deck
import com.example.project_01.Models.Lobby
import com.example.project_01.Models.User

@Database(entities = [Lobby::class, User::class, Deck::class],version = 3,exportSchema = false)

abstract class Database: RoomDatabase() {
    abstract fun LobbyDao(): LobbyDao
    abstract fun DeckDao() : DeckDao
}
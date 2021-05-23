package com.example.project_01.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.project_01.Models.Lobby

@Database(entities = [Lobby::class],version = 1,exportSchema = false)

abstract class Database: RoomDatabase() {
    abstract fun RoomDao(): LobbyDao
}
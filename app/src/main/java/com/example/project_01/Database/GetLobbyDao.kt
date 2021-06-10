package com.example.project_01.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_01.Deserializers.LobbyCredentials
import com.example.project_01.Models.Lobby

@Dao
interface GetLobbyDao {
    @Query("SELECT * from LobbyCredentials")
    fun getAll(): LiveData<MutableList<LobbyCredentials>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(lobby: LobbyCredentials)
}
package com.example.project_01.Database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.project_01.Models.Lobby

/*
Salas creadas por nosotros
 */
@Dao
interface LobbyDao {
    @Query("SELECT * from Lobby")
    fun getAll(): LiveData<MutableList<Lobby>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lobby: Lobby)

    @Query("DELETE FROM Lobby where id = :id")
    fun delete(id: Long)
}

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
    @Query("SELECT * from Lobby where cards is not null")
    fun getAll(): LiveData<MutableList<Lobby>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lobby: Lobby)

    @Query("SELECT * FROM Lobby where room_id= :roomId")
    fun check(roomId: String?): Lobby


    @Query("DELETE FROM Lobby where id = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM Lobby where room_id is null")
    fun getCreatedOffline(): MutableList<Lobby>

    @Query("DELETE FROM Lobby where room_id is null")
    fun clear()

    @Query("UPDATE Lobby SET cards = null where id = :id ")
    fun deleteOffline(id: Long)

    @Query("SELECT * FROM Lobby where cards is null")
    fun getDeleteOffline(): MutableList<Lobby>

    @Query("DELETE FROM Lobby where room_id is null and cards is null")
    fun updateDeletions()

}

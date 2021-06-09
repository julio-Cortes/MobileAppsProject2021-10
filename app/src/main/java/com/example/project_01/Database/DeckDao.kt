package com.example.project_01.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_01.Deserializers.DecksCredentials
import com.example.project_01.Models.Deck


@Dao
interface DeckDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(deck: Deck)

    @Query("SELECT * from Deck")
    fun getAll(): List<Deck>
}
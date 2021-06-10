package com.example.project_01.Deserializers

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LobbyCredentials(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String
) {
}
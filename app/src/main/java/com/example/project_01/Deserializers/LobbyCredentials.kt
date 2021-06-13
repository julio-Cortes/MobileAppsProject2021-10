package com.example.project_01.Deserializers

import androidx.room.Entity
import androidx.room.PrimaryKey

data class LobbyCredentials(
    //val id: String,
    val roomName: String?,
    val message:String?,
    val roomId:String?,
    val members: List<String>?,
    val deck : DecksCredentials?
) {
}
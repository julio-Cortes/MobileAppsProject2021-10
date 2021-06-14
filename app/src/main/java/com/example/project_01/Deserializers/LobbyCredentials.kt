package com.example.project_01.Deserializers

import androidx.room.Entity
import androidx.room.PrimaryKey

data class LobbyCredentials(
    //val id: String,
    val roomId:String?,
    val roomName: String?,
    val password:String?,
    val deck_list : DecksCredentials?,
    val deck : String?,
    val members: List<String>?,
) {
}
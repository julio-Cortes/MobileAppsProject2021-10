package com.example.project_01.Deserializers

data class CreateLobbyCredentials(
    val roomName : String,
    val password : String,
    val deck : DecksCredentials
) {
}
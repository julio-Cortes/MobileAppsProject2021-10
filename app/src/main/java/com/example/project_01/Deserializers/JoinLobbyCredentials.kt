package com.example.project_01.Deserializers

data class JoinLobbyCredentials(
    val message: String,
    val members: List<String>
) {
}
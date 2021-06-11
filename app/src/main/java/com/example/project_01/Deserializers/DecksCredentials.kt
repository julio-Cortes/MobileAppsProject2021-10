package com.example.project_01.Deserializers

data class DecksCredentials(
    val name : String,
    val cards : MutableList<String>
) {
    override fun toString(): String {
        return this.name
    }
}
package com.example.project_01.Deserializers

import com.google.gson.JsonArray

data class DecksCredentials(
    val name : String,
    val cards : MutableList<String>
) {
    override fun toString(): String {
        return this.name
    }

}
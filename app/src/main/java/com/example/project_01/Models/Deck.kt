package com.example.project_01.Models

class Deck(
        val name:String,
        val cards : MutableList<String>
){
    override fun toString(): String {
        return this.name
    }
}
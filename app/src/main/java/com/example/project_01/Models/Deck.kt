package com.example.project_01.Models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.project_01.Deserializers.DecksCredentials
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Deck(
    @PrimaryKey(autoGenerate = true)
    val idDeck : Long = 0,
    val name_deck:String,
    var cards : String?
): Parcelable{
    override fun toString(): String {
        return this.name_deck
    }
    fun toDeckCredentials():DecksCredentials{
        val list = this.cards?.replace("[","")?.replace("]","")?.split(",")
        return DecksCredentials(this.name_deck,list as MutableList<String>)
    }
}
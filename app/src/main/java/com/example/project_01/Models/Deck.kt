package com.example.project_01.Models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Deck(
        @PrimaryKey(autoGenerate = false)
        val name:String,
        val cards : List<String>
): Parcelable{
    override fun toString(): String {
        return this.name
    }
}
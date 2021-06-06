package com.example.project_01.Models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity//(foreignKeys = arrayOf(ForeignKey(entity = Deck::class,
       // parentColumns = arrayOf("name"),
       // childColumns = arrayOf("deck"),
       // onDelete = ForeignKey.CASCADE)))
data class Lobby(
        @PrimaryKey(autoGenerate = true)
        val id:Long = 0,
        val name: String,
        val password: String
        //val deck: Deck
): Parcelable
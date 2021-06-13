package com.example.project_01.Models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Lobby(
        @PrimaryKey(autoGenerate = true)
        val id : Long = 0,
        val room_id:String?,
        val name: String?,
        val password: String?,
        @Embedded val deck: Deck?
): Parcelable
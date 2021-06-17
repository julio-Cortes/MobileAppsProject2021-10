package com.example.project_01.Deserializers

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LobbyCredentials(
    val roomId:String?,
    val roomName: String?,
    val password:String?,
    val deck : DecksCredentials?,
    val deck_string : String?,
    val members: List<String>?,
    val result:List<Members>?
): Parcelable {
}
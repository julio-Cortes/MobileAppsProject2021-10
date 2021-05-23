package com.example.project_01.Models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey
    val id: Int,
    val username: String,
    val password: String,
    val name: String
): Parcelable{

}

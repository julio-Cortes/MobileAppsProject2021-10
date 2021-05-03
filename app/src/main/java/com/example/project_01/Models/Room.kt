package com.example.project_01.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
        val name: String?,
        val password: String?
): Parcelable
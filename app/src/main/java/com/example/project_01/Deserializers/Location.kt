package com.example.project_01.Deserializers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val long : String?,
    val lat : String?,
    val timestamp : String?
): Parcelable {
}
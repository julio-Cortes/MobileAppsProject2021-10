package com.example.project_01.Deserializers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class DecksCredentials(
    val name : String,
    val cards : MutableList<String>
):Parcelable {
}


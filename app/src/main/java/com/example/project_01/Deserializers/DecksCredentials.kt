package com.example.project_01.Deserializers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
data class DecksCredentials(
    val name : String,
    val cards : MutableList<String>
):Parcelable {
    override fun toString(): String {
        return this.name
    }
}


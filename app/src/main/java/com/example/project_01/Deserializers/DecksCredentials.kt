package com.example.project_01.Deserializers

import android.os.Parcelable
import com.google.gson.JsonArray
import kotlinx.parcelize.Parcelize
import org.json.JSONObject


@Parcelize
data class DecksCredentials(
    val name : String,
    val cards : MutableList<String>
):Parcelable {
}


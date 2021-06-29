package com.example.project_01.Deserializers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Members(
    val name:String?,
    val vote:String?,
    val user_Id:String?,
    val username : String?,
    val location : Location?
): Parcelable {
}

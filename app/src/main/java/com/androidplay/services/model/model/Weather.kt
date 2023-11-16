package com.androidplay.services.model.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    @field:Json(name = "id") val id: Int = 0,
    val main: Main,
    @field:Json(name = "name") val name: String = "",
) : Parcelable {
    @Parcelize
    data class Main(@field:Json(name = "temp") val temp: Double = 0.0) : Parcelable
}
package com.androidplay.services.model.model

import com.squareup.moshi.Json

data class Weather(
    @field:Json(name = "id") val id: Int = 0,
    val main: Main,
    @field:Json(name = "name") val name: String = "",
) {
    data class Main(@field:Json(name = "temp") val temp: Double = 0.0)
}
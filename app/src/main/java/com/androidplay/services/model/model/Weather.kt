package com.androidplay.services.model.model

data class Weather(
    val id: Int?,
    val main: Main?,
    val name: String?,
) {
    data class Main(val temp: Double?)
}
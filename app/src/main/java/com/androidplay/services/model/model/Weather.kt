package com.androidplay.services.model.model

data class Weather(
    val id: Int = 0,
    val main: Main,
    val name: String = "",
) {
    data class Main(val temp: Double = 0.0)
}
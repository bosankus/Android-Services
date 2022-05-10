package com.androidplay.services.model.persistance

import com.androidplay.services.model.model.Weather
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {

    suspend fun saveData(weather: Weather)

    suspend fun getAreaName(): Flow<String?>

    suspend fun getTemperature(): Flow<String?>
}
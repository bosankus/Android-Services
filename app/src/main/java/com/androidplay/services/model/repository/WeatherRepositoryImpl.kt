package com.androidplay.services.model.repository

import com.androidplay.services.model.model.Weather
import com.androidplay.services.model.network.WeatherApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 11,March,2022
 */
class WeatherRepositoryImpl(private val api: WeatherApiInterface) : WeatherRepository {

    override suspend fun getWeather(areaName: String): Weather? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getWeather(location = areaName)
                response
            } catch (e: Exception) {
                e.message
                null
            }
        }
    }

    /*companion object {
        private var instance: WeatherRepositoryImpl? = null
        operator fun invoke(): WeatherRepositoryImpl {
            var localInstance = instance
            if (localInstance == null) {
                val api = WeatherApiInterface()
                localInstance = WeatherRepositoryImpl(api)
                instance = localInstance
            }
            return localInstance
        }
    }*/
}
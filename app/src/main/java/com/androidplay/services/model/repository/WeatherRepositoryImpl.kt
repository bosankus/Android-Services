package com.androidplay.services.model.repository

import com.androidplay.services.model.model.Weather
import com.androidplay.services.model.network.WeatherApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 11,March,2022
 */
class WeatherRepositoryImpl(private val api: WeatherApiInterface) : WeatherRepository {

    override suspend fun getWeather(): Flow<Weather?> {
        return flow {
            val result: Weather? = api.getWeather()
            if (result != null && result.main?.temp != null) emit(result)
            else emit(null)
        }.flowOn(Dispatchers.IO)
    }

    companion object {
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
    }
}
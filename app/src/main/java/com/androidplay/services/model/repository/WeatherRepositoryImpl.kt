package com.androidplay.services.model.repository

import com.androidplay.services.model.model.Weather
import com.androidplay.services.model.network.WeatherApiService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 11,March,2022
 */

@Singleton
class WeatherRepositoryImpl @Inject constructor(private val api: WeatherApiService) :
    WeatherRepository {

    override suspend fun getWeather(areaName: String): Weather? =
        api.getWeather(location = areaName)

}

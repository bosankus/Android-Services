package com.androidplay.services.presenter

import com.androidplay.services.model.model.Weather
import com.androidplay.services.model.repository.WeatherRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 12,March,2022
 */
class MainInteractor : CoroutineScope {

    private val repository: WeatherRepositoryImpl = WeatherRepositoryImpl()
    private val job = SupervisorJob()

    interface onFinishedListener {
        fun onSuccess(weather: Weather)
        fun onFailed(error: String)
    }

    fun requestData(onFinishedListener: onFinishedListener) {
        launch {
            val weather: Weather? = repository.getWeather()
            weather?.let { onFinishedListener.onSuccess(it) }
                ?: onFinishedListener.onFailed("No details found")
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job
}
package com.androidplay.services.view.main

import com.androidplay.services.BaseContract
import com.androidplay.services.model.model.Weather
import com.androidplay.services.model.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 12,March,2022
 */
class MainInteractorImpl(private val repository: WeatherRepository) : BaseContract.Interactor,
    CoroutineScope {

    private val job = SupervisorJob()

    override fun requestData(
        areaName: String,
        onFinishedListener: BaseContract.Interactor.OnFinishedListener?
    ) {
        launch {
            val weather: Weather? = repository.getWeather(areaName)
            weather?.let { onFinishedListener?.onSuccess(it) }
                ?: onFinishedListener?.onFailed("No details found")
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job
}
package com.androidplay.services.Interactor

import com.androidplay.services.contract.MainContract
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
class MainInteractor : MainContract.Interactor, CoroutineScope {

    private val repository: WeatherRepositoryImpl = WeatherRepositoryImpl()
    private val job = SupervisorJob()

    override fun requestData(
        areaName: String,
        onFinishedListener: MainContract.Interactor.OnFinishedListener?
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
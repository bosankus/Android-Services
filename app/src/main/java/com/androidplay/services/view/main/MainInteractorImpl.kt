package com.androidplay.services.view.main

import com.androidplay.services.BaseContract
import com.androidplay.services.model.model.Weather
import com.androidplay.services.model.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 12,March,2022
 */
class MainInteractorImpl(
    private val repository: WeatherRepository,
    private val scope: CoroutineScope
) : BaseContract.Interactor {

    override fun requestData(
        areaName: String,
        onFinishedListener: BaseContract.Interactor.OnFinishedListener?
    ) {
        scope.launch {
            try {
                val weather: Weather? = repository.getWeather(areaName)
                if (weather != null && weather.name != "") onFinishedListener?.onSuccess(weather)
                else onFinishedListener?.onFailed("Urgh...Looks like our satellites are having coffee time!")
            } catch (e: HttpException) {
                when (e.code()) {
                    401 -> onFinishedListener?.onFailed("Unauthorised access!")
                    500 -> onFinishedListener?.onFailed("Umm... Looks like server issue.")
                    else -> onFinishedListener?.onFailed("Something went wrong!")
                }
            } catch (e: IOException) {
                onFinishedListener?.onFailed("An error occurred while connecting to server.")
            }
        }
    }

    override fun cleanUp() {
        if (scope.isActive) scope.cancel()
        else return
    }

}
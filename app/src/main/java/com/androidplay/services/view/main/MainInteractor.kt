package com.androidplay.services.view.main

import com.androidplay.services.BaseContract
import com.androidplay.services.dispatcher.DispatcherProvider
import com.androidplay.services.model.model.Weather
import com.androidplay.services.model.repository.WeatherRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 12,March,2022
 */
class MainInteractor(
    private val repository: WeatherRepository,
    private val dispatcher: DispatcherProvider,
    private val scope: CoroutineScope,
) : BaseContract.Interactor {

    override fun requestData(
        areaName: String,
        onFinishedListener: BaseContract.Interactor.OnFinishedListener
    ) {
        scope.launch {
            try {
                val weather: Weather? = repository.getWeather(areaName)
                withContext(dispatcher.main) {
                    if (weather != null && weather.name != "") onFinishedListener.onSuccess(weather)
                    else onFinishedListener.onFailed("Urgh...Looks like our satellites are having coffee break!")
                }
            } catch (e: HttpException) {
                when (e.code()) {
                    401 -> onFinishedListener.onFailed("Unauthorised access!")
                    400, 404 -> onFinishedListener.onFailed("City not found")
                    500 -> onFinishedListener.onFailed("Umm... Looks like server issue.")
                    else -> onFinishedListener.onFailed("Something went wrong!")
                }
            } catch (e: IOException) {
                onFinishedListener.onFailed("An error occurred while connecting to server.")
            } catch (e: Exception) {
                onFinishedListener.onFailed("Something went wrong!")
            } catch (e: Throwable) {
                onFinishedListener.onFailed("Something went wrong!")
            }
        }
    }

    override fun cleanUp() {
        if (scope.isActive) scope.cancel()
        else return
    }

}
package com.androidplay.services.weatheralarmhost

import android.util.Log
import android.view.View
import com.androidplay.services.weatheralarm.contract.WeatherAlarmConfiguration

class WeatherAlarmConfigurationImpl : WeatherAlarmConfiguration {

    override val saveButtonColor: View? = null

    override fun onButtonClicked() {
        Log.d("Weather-alarm", "onButtonClicked: Click event triggered from host")
    }
}
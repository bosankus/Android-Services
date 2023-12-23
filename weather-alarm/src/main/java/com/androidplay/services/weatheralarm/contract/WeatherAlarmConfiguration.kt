package com.androidplay.services.weatheralarm.contract

import android.view.View

interface WeatherAlarmConfiguration: OnWeatherAlarmSaveButtonClickListener {
    val saveButtonColor: View?
}

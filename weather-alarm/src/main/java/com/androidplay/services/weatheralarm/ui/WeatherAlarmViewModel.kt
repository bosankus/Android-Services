package com.androidplay.services.weatheralarm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidplay.services.weatheralarm.contract.WeatherAlarmConfiguration
import javax.inject.Inject

class WeatherAlarmViewModel @Inject constructor(
    private val configuration: WeatherAlarmConfiguration,
) : ViewModel() {

    private val _selectedTime = MutableLiveData<String>()
    val selectedTime: LiveData<String> = _selectedTime

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> = _selectedDate

    fun setDate(date: String) = _selectedDate.postValue(date)

    fun setTime(time: String) = _selectedTime.postValue(time)

    fun performClickEvent() = configuration.onButtonClicked()
}
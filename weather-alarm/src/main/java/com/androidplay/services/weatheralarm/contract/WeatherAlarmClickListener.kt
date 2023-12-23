package com.androidplay.services.weatheralarm.contract

interface OnWeatherAlarmSaveButtonClickListener {
    /**
     * Whenever save button is clicked, host will be notified to take action
     */
    fun onButtonClicked()
}
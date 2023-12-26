package com.androidplay.services.utils

object Constants {

    const val WEATHER_URL = "https://api.openweathermap.org/"
    const val KELVIN_CONSTANT = 273.15
    const val DEFAULT_AREA = "Delhi"
    const val DATASTORE_PREF_NAME = "weather_object"

    // Alarm constants
    const val ALARM_INTENT_ACTION = "alarm_notification_action"
    const val ALARM_INTENT_TEXT = "alarm_notification_text"
    const val ALARM_CONTROLLER_REQ_CODE = 101

    // Feature flags
    const val FETCH_TEMPERATURE_FLAG = "fetch_temperature"
    const val ALARM_SCHEDULE_FLAG = "alarm_schedule"
    const val FOREGROUND_SERVICE_FLAG = "foreground_service"
}
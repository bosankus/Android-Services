package com.androidplay.services.di.module

import com.androidplay.services.weatheralarm.contract.WeatherAlarmConfiguration
import com.androidplay.services.weatheralarmhost.WeatherAlarmConfigurationImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherAlarmConfigurationModule {

    @Provides
    @Singleton
    fun provideWeatherAlarmConfiguration(): WeatherAlarmConfiguration =
        WeatherAlarmConfigurationImpl()
}
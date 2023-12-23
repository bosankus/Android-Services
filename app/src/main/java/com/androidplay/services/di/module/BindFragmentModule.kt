package com.androidplay.services.di.module

import com.androidplay.services.ui.AppFragment
import com.androidplay.services.weatheralarm.ui.WeatherAlarmFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface BindFragmentModule {

    @ContributesAndroidInjector(
        modules = [
            MainModule::class
        ]
    )
    fun bindAppFragment(): AppFragment

    @ContributesAndroidInjector
    fun bindWeatherAlarmFragment(): WeatherAlarmFragment
}
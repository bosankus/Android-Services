package com.androidplay.services

import android.app.Application
import com.androidplay.services.di.component.AppComponent
import com.androidplay.services.di.component.DaggerAppComponent
import com.androidplay.services.di.module.AppModule

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 15,March,2022
 */
class WeatherifyMVPApplication : Application() {

    lateinit var weatherifyMVPComponent: AppComponent

    private fun initDagger(app: WeatherifyMVPApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()

    override fun onCreate() {
        super.onCreate()
        weatherifyMVPComponent = initDagger(this)
    }
}
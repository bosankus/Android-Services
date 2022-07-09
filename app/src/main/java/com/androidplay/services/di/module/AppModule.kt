package com.androidplay.services.di.module

import android.content.Context
import com.androidplay.services.application.WeatherifyMVPApplication
import com.androidplay.services.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 15,March,2022
 */

@Module
class AppModule(private val application: WeatherifyMVPApplication) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Context = application
}
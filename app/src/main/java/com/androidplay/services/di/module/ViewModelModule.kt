package com.androidplay.services.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidplay.services.weatheralarm.ui.WeatherAlarmViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherAlarmViewModel::class)
    abstract fun bindWeatherAlarmViewModel(weatherAlarmViewModel: WeatherAlarmViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
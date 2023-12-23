package com.androidplay.services.di.component

import com.androidplay.services.WeatherifyMVPApplication
import com.androidplay.services.di.module.BindFragmentModule
import com.androidplay.services.di.module.ViewModelModule
import com.androidplay.services.di.module.WeatherAlarmConfigurationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 17,March,2022
 */
@Singleton
@Component(
    modules = [
        BindFragmentModule::class,
        AndroidInjectionModule::class,
        WeatherAlarmConfigurationModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(application: WeatherifyMVPApplication): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: DaggerApplication?)
}
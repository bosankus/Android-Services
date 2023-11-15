package com.androidplay.services.di.component

import com.androidplay.services.MainActivity
import com.androidplay.services.di.module.AppModule
import com.androidplay.services.di.module.BindingModule
import com.androidplay.services.di.module.ContextModule
import com.androidplay.services.di.module.MainModule
import dagger.Component
import javax.inject.Singleton

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 17,March,2022
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        ContextModule::class,
        MainModule::class,
        BindingModule::class
    ]
)
interface AppComponent {

    fun inject(target: MainActivity)
}
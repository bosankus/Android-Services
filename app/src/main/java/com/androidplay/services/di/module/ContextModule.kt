package com.androidplay.services.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 15,March,2022
 */

@Module
class ContextModule(private val context: Context) {

    @Provides
    fun provideContext(): Context = context
}
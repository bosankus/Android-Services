package com.androidplay.services.di.module

import com.androidplay.services.dispatcher.DispatcherProvider
import com.androidplay.services.model.interceptors.ConnectivityInterceptor
import com.androidplay.services.model.network.WeatherApiService
import com.androidplay.services.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 15,March,2022
 */
@Module
class MainModule {

    @Singleton
    @Provides
    fun provideHttpClient(connectivityInterceptor: ConnectivityInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(connectivityInterceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideWeatherApiInterface(moshi: Moshi, client: OkHttpClient): WeatherApiService =
        Retrofit.Builder()
            .baseUrl(Constants.WEATHER_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
            .create(WeatherApiService::class.java)

    @Provides
    fun provideCoroutineScope(dispatcher: DispatcherProvider): CoroutineScope =
        CoroutineScope(Job() + dispatcher.io)
}
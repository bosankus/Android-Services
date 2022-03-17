package com.androidplay.services.di.module

import android.content.Context
import com.androidplay.services.view.main.MainInteractorImpl
import com.androidplay.services.BaseContract
import com.androidplay.services.model.network.ConnectivityInterceptor
import com.androidplay.services.model.network.ConnectivityInterceptorImpl
import com.androidplay.services.model.network.WeatherApiInterface
import com.androidplay.services.model.repository.WeatherRepository
import com.androidplay.services.model.repository.WeatherRepositoryImpl
import com.androidplay.services.view.main.MainPresenterImpl
import com.androidplay.services.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
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

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(context: Context): ConnectivityInterceptor =
        ConnectivityInterceptorImpl(context)

    @Provides
    fun provideHttpClient(connectivityInterceptor: ConnectivityInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(connectivityInterceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideWeatherApiInterface(moshi: Moshi, client: OkHttpClient): WeatherApiInterface =
        Retrofit.Builder()
            .baseUrl(Constants.WEATHER_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
            .create(WeatherApiInterface::class.java)

    @Provides
    fun provideRepository(apiInterface: WeatherApiInterface): WeatherRepository =
        WeatherRepositoryImpl(apiInterface)

    @Provides
    fun provideInteractor(repository: WeatherRepository): BaseContract.Interactor =
        MainInteractorImpl(repository)

    @Provides
    fun providePresenter(interactor: BaseContract.Interactor): BaseContract.Presenter =
        MainPresenterImpl(interactor)
}
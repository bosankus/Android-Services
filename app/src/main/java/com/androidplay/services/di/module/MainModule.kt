package com.androidplay.services.di.module

import android.content.Context
import com.androidplay.services.BaseContract
import com.androidplay.services.MainInteractor
import com.androidplay.services.MainPresenter
import com.androidplay.services.WeatherifyMVPApplication
import com.androidplay.services.dispatcher.DispatcherProvider
import com.androidplay.services.dispatcher.DispatcherProviderImpl
import com.androidplay.services.model.interceptors.ConnectivityInterceptor
import com.androidplay.services.model.network.WeatherApiService
import com.androidplay.services.model.persistance.DataStoreManager
import com.androidplay.services.model.persistance.DataStoreManagerImpl
import com.androidplay.services.model.repository.WeatherRepository
import com.androidplay.services.model.repository.WeatherRepositoryImpl
import com.androidplay.services.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 15,March,2022
 */
@Module
object MainModule {

    @Provides
    fun provideApplication(application: WeatherifyMVPApplication): Context =
        application.applicationContext

    @Provides
    fun provideConnectivityInterceptor(
        context: Context
    ): Interceptor = ConnectivityInterceptor(context)

    @Provides
    fun provideHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

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
    fun provideCoroutineScope(
        dispatcher: DispatcherProvider
    ): CoroutineScope = CoroutineScope(Job() + dispatcher.io)

    @Provides
    fun provideWeatherRepository(
        apiService: WeatherApiService
    ): WeatherRepository = WeatherRepositoryImpl(apiService)

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider =
        DispatcherProviderImpl()

    @Provides
    fun provideMainInteractor(
        repository: WeatherRepository,
        dispatcher: DispatcherProvider,
        scope: CoroutineScope
    ): BaseContract.Interactor = MainInteractor(repository, dispatcher, scope)

    @Provides
    fun provideMainPresenter(
        interactor: BaseContract.Interactor
    ): BaseContract.Presenter = MainPresenter(interactor)

    @Provides
    fun provideDataStoreManager(
        context: Context
    ): DataStoreManager = DataStoreManagerImpl(context)
}
package com.androidplay.services.di.module

import com.androidplay.services.BaseContract
import com.androidplay.services.dispatcher.DispatcherProvider
import com.androidplay.services.dispatcher.DispatcherProviderImpl
import com.androidplay.services.model.interceptors.ConnectivityInterceptor
import com.androidplay.services.model.persistance.DataStoreManager
import com.androidplay.services.model.persistance.DataStoreManagerImpl
import com.androidplay.services.model.repository.WeatherRepository
import com.androidplay.services.model.repository.WeatherRepositoryImpl
import com.androidplay.services.view.main.MainInteractor
import com.androidplay.services.view.main.MainPresenter
import dagger.Binds
import dagger.Module
import okhttp3.Interceptor

@Module
abstract class BindingModule {

    @Binds
    abstract fun bindDispatcherProvider(dispatcherProviderImpl: DispatcherProviderImpl): DispatcherProvider

    @Binds
    abstract fun bindRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    abstract fun bindConnectivityInterceptor(connectivityInterceptor: ConnectivityInterceptor): Interceptor

    @Binds
    abstract fun bindInteractor(mainInteractor: MainInteractor): BaseContract.Interactor

    @Binds
    abstract fun bindPresenter(mainPresenter: MainPresenter): BaseContract.Presenter

    @Binds
    abstract fun bindDataStore(dataStoreManagerImpl: DataStoreManagerImpl): DataStoreManager
}
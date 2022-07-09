package com.androidplay.services.view.main

import android.content.res.Resources
import com.androidplay.services.BaseContract
import com.androidplay.services.R
import com.androidplay.services.dispatcher.DispatcherProvider
import com.androidplay.services.dispatcher.DispatcherProviderImpl
import com.androidplay.services.model.model.Weather
import com.androidplay.services.model.network.WeatherApiInterface
import com.androidplay.services.model.repository.WeatherRepository
import com.androidplay.services.model.repository.WeatherRepositoryImpl
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainInteractorImplTest {

    private val listener: BaseContract.Interactor.OnFinishedListener = mock()

    private val dispatcher: DispatcherProvider = mock()

    private val scope: CoroutineScope = mock()

    private val repository: WeatherRepository = mock()

    @Spy
    private val interactor: BaseContract.Interactor = MainInteractorImpl(repository, dispatcher, scope)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }


    @Test
    fun requestData_onEmptyLocationParameter_returnsFailed() {

        runBlocking {
            `when`(interactor.requestData("", listener)).thenReturn(listener.onFailed(anyString()))
        }
    }

}

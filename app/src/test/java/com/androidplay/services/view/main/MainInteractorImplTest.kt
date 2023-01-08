package com.androidplay.services.view.main

import com.androidplay.services.BaseContract
import com.androidplay.services.dispatcher.DispatcherProvider
import com.androidplay.services.model.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
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

    @Mock
    private lateinit var listener: BaseContract.Interactor.OnFinishedListener

    @Mock
    private lateinit var dispatcher: DispatcherProvider

    @Mock
    private lateinit var scope: CoroutineScope

    @Mock
    private lateinit var repository: WeatherRepository

    @Spy
    private lateinit var interactor: BaseContract.Interactor

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        interactor = MainInteractorImpl(repository, dispatcher, scope)
    }


    @Test
    fun requestData_onEmptyLocationParameter_returnsFailed() {

        runBlocking {
            `when`(interactor.requestData("", listener)).thenReturn(listener.onFailed(anyString()))
        }
    }

}

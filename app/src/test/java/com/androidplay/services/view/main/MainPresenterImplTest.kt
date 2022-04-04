package com.androidplay.services.view.main

import com.androidplay.services.BaseContract
import com.androidplay.services.model.model.Weather
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class MainPresenterImplTest {

    @Mock
    private lateinit var view: BaseContract.View

    private val interactor: BaseContract.Interactor = mock(MainInteractorImpl::class.java)

    @Spy
    private val presenter = MainPresenterImpl(interactor)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        presenter.attach(view)
    }


    @Test
    fun `verify_attachView_isCalled`() {
        verify(presenter, times(1)).attach(view)
    }


    @Test
    fun `verify_getData_isCalled`()  {
        val areaName = "Kolkata"

        presenter.getData(areaName)

        verify(view, times(1)).showProgress()
    }


    /*@Test
    fun `getData_returns_successResponse`() {
        val testWeatherMain = Weather.Main(306.12)
        val testWeather = Weather(721, testWeatherMain, "Kolkata")

        verify(presenter, times(1)).onSuccess(testWeather)
    }*/
}
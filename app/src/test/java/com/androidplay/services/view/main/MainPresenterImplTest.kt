package com.androidplay.services.view.main

import com.androidplay.services.BaseContract
import com.androidplay.services.model.model.Weather
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterImplTest {

    private val view: BaseContract.View = mock()
    private val interactor: BaseContract.Interactor = mock()
    private val onFinishedListener: BaseContract.Interactor.OnFinishedListener = mock()

    @Spy
    private val presenter: BaseContract.Presenter = MainPresenterImpl(interactor)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        presenter.attach(view)
    }


    @Test
    fun verify_attachView_isCalled() {
        verify(presenter, times(1)).attach(view)
    }


    @Test
    fun verify_onGetDataMethodCallWithCorrectAreaName_followsCorrectMethodCalls_returnsSuccessResponse() {
        runBlocking {
            {
                val areaName = "Kolkata"
                val testWeather = Weather(721, 305.12, areaName)
                val inOrder = inOrder(view, interactor)
                presenter.getData(areaName)

                inOrder.apply {
                    verify(view, times(1)).showProgress()
                    verify(interactor, times(1)).requestData(areaName, onFinishedListener)
                    verify(onFinishedListener, times(1)).onSuccess(testWeather)
                    verify(view, times(1)).setSuccessData(testWeather)
                    verify(view, times(1)).hideProgress()
                }
            }
        }
    }


    @Test
    fun verify_onGetDataMethodCallWithIncorrectAreaName_followsCorrectMethodCalls_returnsFailResponse() {
        runBlocking {
            {
                val areaName = "K"
                val inOrder = inOrder(view, interactor)
                presenter.getData(areaName)

                inOrder.apply {
                    verify(view, times(1)).showProgress()
                    verify(interactor, times(1)).requestData(areaName, onFinishedListener)
                    verify(onFinishedListener, times(1)).onFailed("City not found")
                    verify(view, times(1)).setFailureData("City not found")
                    verify(view, times(1)).hideProgress()
                }
            }
        }
    }


    @Test
    fun getData_returns_successResponse() {
        val mockedResponse = view.setFailureData("City not found")
        presenter.getData("k")
        verify(view, times(1)).setFailureData("City not found")
    }
}
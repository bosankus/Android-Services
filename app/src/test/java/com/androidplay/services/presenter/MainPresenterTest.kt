package com.androidplay.services.presenter

import com.androidplay.services.BaseContract
import com.androidplay.services.view.main.MainPresenterImpl
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 14,March,2022
 */
@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    private lateinit var view: BaseContract.View

    @Mock
    private lateinit var interactor: BaseContract.Interactor

    @Spy
    private var presenter: MainPresenterImpl = MainPresenterImpl(interactor)

    @Test
    fun getData_calls_onSuccessFinishedListener_on_positive_response () {
        Mockito.`when`(presenter.getData("Kolkata"))
            .thenReturn(null)
    }

}
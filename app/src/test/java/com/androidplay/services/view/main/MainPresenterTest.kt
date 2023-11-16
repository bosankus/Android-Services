package com.androidplay.services.view.main

import com.androidplay.services.BaseContract
import com.androidplay.services.MainPresenter
import org.junit.After
import org.junit.Before
import org.mockito.Mock
import org.mockito.Spy

class MainPresenterTest {

    @Mock
    private lateinit var interactor: BaseContract.Interactor
    @Spy
    private lateinit var presenter: BaseContract.Presenter

    @Before
    fun setUp() {
        presenter = MainPresenter(interactor)
    }

    @After
    fun tearDown() {

    }
}
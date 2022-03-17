package com.androidplay.services.presenter

import com.androidplay.services.contract.MainContract
import com.androidplay.services.model.model.Weather

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 12,March,2022
 */
class MainPresenterImpl(private val interactor: MainContract.Interactor) : MainContract.Presenter,
    MainContract.Interactor.OnFinishedListener {

    private lateinit var view: MainContract.View

    override fun getData(areaName: String) {
        view.showProgress()
        interactor.requestData(areaName, this)
    }

    override fun onSuccess(weather: Weather) {
        view.hideProgress()
        view.setSuccessData(weather)
    }

    override fun onFailed(error: String) {
        view.hideProgress()
        view.setFailureData(error)
    }

}
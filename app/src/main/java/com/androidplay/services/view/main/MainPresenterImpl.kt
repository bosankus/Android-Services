package com.androidplay.services.view.main

import com.androidplay.services.BaseContract
import com.androidplay.services.model.model.Weather

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 12,March,2022
 */
class MainPresenterImpl(private val interactor: BaseContract.Interactor) : BaseContract.Presenter,
    BaseContract.Interactor.OnFinishedListener {

    private var view: BaseContract.View? = null

    override fun attach(view: BaseContract.View) {
        this.view = view
    }

    override fun getData(areaName: String) {
        view?.showProgress()
        interactor.requestData(areaName, this)
    }

    override fun onSuccess(weather: Weather) {
        view?.hideProgress()
        view?.setSuccessData(weather)
    }

    override fun onFailed(error: String) {
        view?.hideProgress()
        view?.setFailureData(error)
    }

    override fun cleanUp() {
        this.view = null
        interactor.cleanUp()
    }
}
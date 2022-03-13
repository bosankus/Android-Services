package com.androidplay.services.presenter

import com.androidplay.services.model.model.Weather

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 12,March,2022
 */
class MainPresenter(
    private var view: MainView?,
    private val interactor: MainInteractor
) : MainInteractor.onFinishedListener {

    fun getData() {
        view?.showProgress()
        interactor.requestData(this)
    }

    override fun onSuccess(weather: Weather) {
        view?.hideProgress()
        view?.setData(weather)
    }

    override fun onFailed(error: String) {
        view?.hideProgress()
        view?.setFailureError(error)
    }

    fun onDestroy() {
        view = null
    }


}
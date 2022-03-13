package com.androidplay.services.presenter

import com.androidplay.services.Interactor.MainInteractor
import com.androidplay.services.contract.MainContract
import com.androidplay.services.model.model.Weather

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 12,March,2022
 */
class MainPresenter(
    private var view: MainContract.View?,
    private val interactor: MainInteractor
) : MainInteractor.onFinishedListener {

    fun getData() {
        view?.showProgress()
        interactor.requestData(this)
    }

    override fun onSuccess(weather: Weather) {
        view?.hideProgress()
        view?.setSuccessData(weather)
    }

    override fun onFailed(error: String) {
        view?.hideProgress()
        view?.setFailureData(error)
    }

    fun onDestroy() {
        view = null
    }


}
package com.androidplay.services.presenter

import com.androidplay.services.model.model.Weather

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 12,March,2022
 */
interface MainView {

    fun setData(weather: Weather)

    fun setFailureError(error: String)

    fun showProgress()

    fun hideProgress()

    fun onDestroy()
}
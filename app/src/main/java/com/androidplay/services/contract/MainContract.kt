package com.androidplay.services.contract

import com.androidplay.services.model.model.Weather

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 12,March,2022
 */
interface MainContract {

    interface View {

        // method called when weather details are fetched and needs to update UI
        fun setSuccessData(weather: Weather)

        // method called when weather details fetching has failed
        fun setFailureData(error: String)

        // method to show progress bar when fetching weather details
        fun showProgress()

        // method to hide progress bar when weather details is fetched or found any when fetching
        fun hideProgress()
    }


    interface Interactor {

        // nested interface to be
        interface OnFinishedListener {
            // function to be called
            // once the Handler of Model class
            // completes its execution
            fun onSuccess(weather: Weather)
            fun onFailed(error: String)
        }

        // method to make network request to repository, and returns success or failure
        fun requestData(areaName: String, onFinishedListener: OnFinishedListener?)
    }


    interface Presenter {

        // method called when activity opens
        fun getData(areaName: String)

        // method called when mainActivity is destroyed
        fun onDestroy()
    }
}
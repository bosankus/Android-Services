package com.androidplay.services.model.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class NetworkErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }

    private fun handleResponse() {

    }
}

enum class ErrorType {
    NETWORK_ERROR
}
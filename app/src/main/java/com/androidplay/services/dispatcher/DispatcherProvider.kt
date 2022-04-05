package com.androidplay.services.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val main: CoroutineDispatcher

    val io: CoroutineDispatcher
}
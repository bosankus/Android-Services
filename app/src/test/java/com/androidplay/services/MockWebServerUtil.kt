package com.androidplay.services

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

object MockWebServerUtil {

    internal fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResource(fileName)?.readText()
        inputStream?.let { body ->
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(body)
            )
        }
    }
}

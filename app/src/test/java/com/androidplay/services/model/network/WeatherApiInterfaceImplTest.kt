package com.androidplay.services.model.network

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class WeatherApiInterfaceImplTest {

    private lateinit var service: WeatherApiInterface
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherApiInterface::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            server.enqueue(mockResponse)
        }
    }


    @Test
    fun getWeather_withCorrectAreaName_returnsSuccessResponse() {
        runBlocking {
            enqueueMockResponse("SuccessResponse.json")
            val response = service.getWeather("Kolkata")
            val request = server.takeRequest()
            assertThat(response).isNotNull()
            assertThat(request.path).isEqualTo("/weather?q=Ko&APPID=ad102b2242e9f1c84075385ae4a91116")
        }
    }


    @Test
    fun getWeather_withCorrectAreaName_returnsFailureResponse() {
        runBlocking {
            enqueueMockResponse("FailureResponse.json")
            val response = service.getWeather("Ko")
            val request = server.takeRequest()
            assertThat(response).isNotNull()
            assertThat(request.path).isEqualTo("/weather?q=Ko&APPID=ad102b2242e9f1c84075385ae4a91116")
        }
    }


    @After
    fun tearDown() {
        server.shutdown()
    }
}
package com.androidplay.services.model.network

import com.androidplay.services.BuildConfig
import com.androidplay.services.MainCoroutineRule
import com.androidplay.services.MockWebServerUtil.enqueueResponse
import com.androidplay.services.model.model.Weather
import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherApiServiceTest {

    private val mockWebServer = MockWebServer()
    private lateinit var client: OkHttpClient
    private lateinit var moshi: Moshi
    private lateinit var weatherApiService: WeatherApiService

    @get: Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        client = OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .connectTimeout(3, TimeUnit.SECONDS)
            .build()

        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        weatherApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(WeatherApiService::class.java)

        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getWeather with correct city name, returns success with 200 code`(): Unit =
        mainCoroutineRule.testScope.runTest {
            mockWebServer.enqueueResponse("SuccessResponse.json", SUCCESS_CODE)
            val actualResponse = weatherApiService.getWeather(location = "Kolkata")
            val request = mockWebServer.takeRequest()
            val expectedResponse = Weather(
                id = 1275004,
                main = Weather.Main(temp = 298.12),
                name = "Kolkata"
            )
            assertThat(actualResponse!!.id != 0).isTrue()
            assertThat(request.path).isEqualTo("/data/2.5/weather?q=Kolkata&APPID=${BuildConfig.WEATHER_KEY}")
            assertThat(actualResponse).isEqualTo(expectedResponse)
        }

    @Test(expected = HttpException::class)
    fun `getWeather with incorrect city name throws HttpException`(): Unit =
        mainCoroutineRule.testScope.runTest {
            mockWebServer.enqueueResponse("FailureResponse.json", ERROR_CODE)
            weatherApiService.getWeather("Ko")
        }

    companion object {
        private const val SUCCESS_CODE = 200
        private const val ERROR_CODE = 404
    }
}

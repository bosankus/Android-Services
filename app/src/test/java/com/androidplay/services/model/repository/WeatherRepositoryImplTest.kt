package com.androidplay.services.model.repository

import com.androidplay.services.model.model.Weather
import com.androidplay.services.model.network.WeatherApiInterface
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryImplTest {

    @Mock
    private lateinit var api: WeatherApiInterface

    @Spy
    private val repository: WeatherRepository = WeatherRepositoryImpl(api)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getWeather_calls_apiSuccessfully() {
        runBlocking {
            val areaName = "Kolkata"
            repository.getWeather(areaName)
            verify(api, times(1)).getWeather(areaName)
        }
    }

    @Test
    fun getWeather_onWrongLocationArgument_returnsNullWeatherId() {
        runBlocking {
            val areaName = "Ko"
            val result: Weather? = repository.getWeather(areaName)
            assertThat(result?.id).isNull()
        }
    }

}
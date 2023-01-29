package com.androidplay.services.model.repository

import com.androidplay.services.model.model.Weather
import com.androidplay.services.model.network.WeatherApiService
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
    private lateinit var api: WeatherApiService

    @Spy
    private lateinit var repository: WeatherRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = WeatherRepositoryImpl(api)
    }

    @Test
    fun `verify that getWeather hits main API exactly 1 time successfully`() {
        runBlocking {
            val areaName = "Kolkata"
            repository.getWeather(areaName)
            verify(api, times(1)).getWeather(areaName)
        }
    }

    @Test
    fun `assert that passing wrong city name returns null weather id`() {
        runBlocking {
            val areaName = "Ko"
            val result: Weather? = repository.getWeather(areaName)
            assertThat(result?.id).isNull()
        }
    }

}
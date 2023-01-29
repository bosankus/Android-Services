package com.androidplay.services.model.network

import com.androidplay.services.BuildConfig
import com.androidplay.services.model.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Author: Ankush Bose
 * Company: Androidplay.in
 * Created on: 11,March,2022
 */
interface WeatherApiService {

    /*implementation method to get current temperature of hardcoded city*/
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") location: String,
        @Query("APPID") AppId: String = BuildConfig.WEATHER_KEY
    ): Weather?

    /*companion object {

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


        // TODO: Provide okHttpClient with network interceptors via DI
        operator fun invoke(): WeatherApiInterface {
            return Retrofit.Builder()
                .baseUrl(WEATHER_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(WeatherApiInterface::class.java)
        }
    }*/
}
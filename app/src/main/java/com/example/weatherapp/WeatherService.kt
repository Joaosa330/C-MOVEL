package com.example.weatherapp

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("721d7638793f057d376a24399df55ec4") apiKey: String
    ): WeatherResponse
}

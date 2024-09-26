package com.example.weather_app.data.remote

import com.example.weather_app.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast?")
    suspend fun getHourlyWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("hourly") hourly: String = "temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl"
    ): WeatherDto
}
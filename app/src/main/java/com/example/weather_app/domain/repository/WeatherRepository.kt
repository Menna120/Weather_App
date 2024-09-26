package com.example.weather_app.domain.repository

import com.example.weather_app.domain.util.RemoteResponse
import com.example.weather_app.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getHourlyWeather(lat: Double, lon: Double): RemoteResponse<WeatherInfo>
}
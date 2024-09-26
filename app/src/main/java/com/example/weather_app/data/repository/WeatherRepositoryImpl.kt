package com.example.weather_app.data.repository

import com.example.weather_app.data.mapper.toWeatherInfo
import com.example.weather_app.data.remote.WeatherApi
import com.example.weather_app.domain.repository.WeatherRepository
import com.example.weather_app.domain.util.RemoteResponse
import com.example.weather_app.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getHourlyWeather(lat: Double, lon: Double): RemoteResponse<WeatherInfo> {
        return try {
            RemoteResponse.Success(
                data = api.getHourlyWeather(lat, lon).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            RemoteResponse.Error(e.message ?: "An unknown error occurred.")
        }
    }
}
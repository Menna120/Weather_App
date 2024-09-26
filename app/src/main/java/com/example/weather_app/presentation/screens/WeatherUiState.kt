package com.example.weather_app.presentation.screens

import com.example.weather_app.presentation.model.Precipitations
import com.example.weather_app.presentation.model.TodayTemperatures
import com.example.weather_app.presentation.model.WeatherMeasures
import com.example.weather_app.presentation.model.WeekForecast

sealed class WeatherUiState {
    data object Loading : WeatherUiState()
    data class Success(
        val currentTempIcon:Int,
        val precipitations: Precipitations,
        val todayTemperatures: TodayTemperatures,
        val weatherMeasures: WeatherMeasures,
        val weekForecast: List<WeekForecast>
    ) : WeatherUiState()

    data class Error(val message: String) : WeatherUiState()
}
package com.example.weather_app.presentation.model

import androidx.annotation.DrawableRes

data class WeekForecast(
    val day: String,
    val maxTemp: Int,
    val minTemp: Int,
    @DrawableRes val iconRes: Int,
)

package com.example.weather_app.presentation.model

import androidx.annotation.DrawableRes

data class Precipitations(
    @DrawableRes val iconRes: Int,
    val currentTemp: Int,
    val weatherDesc: String,
    val maxTemp: Int,
    val minTemp: Int,
)

package com.example.weather_app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HourlyUnits(
    @SerializedName("temperature_2m") val temperature: String,
    @SerializedName("relativehumidity_2m") val humidity: String,
    @SerializedName("weathercode") val weatherCode: String,
    @SerializedName("windspeed_10m") val windSpeed: String,
    @SerializedName("pressure_msl") val pressure: String,
    val time: String
)
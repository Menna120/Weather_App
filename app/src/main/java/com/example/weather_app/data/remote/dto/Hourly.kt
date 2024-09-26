package com.example.weather_app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Hourly(
    @SerializedName("temperature_2m") val temperature: List<Double>,
    @SerializedName("relativehumidity_2m") val humidity: List<Int>,
    @SerializedName("windspeed_10m") val windSpeed: List<Double>,
    @SerializedName("pressure_msl") val pressure: List<Double>,
    @SerializedName("weathercode") val weatherCode: List<Int>,
    val time: List<String>
)
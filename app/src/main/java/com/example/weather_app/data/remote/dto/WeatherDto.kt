package com.example.weather_app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("timezone_abbreviation") val timezoneAbbreviation: String,
    @SerializedName("utc_offset_seconds") val utcOffsetSeconds: Int,
    @SerializedName("generationtime_ms") val generationTime: Double,
    @SerializedName("hourly_units") val hourlyUnits: HourlyUnits,
    val elevation: Int,
    val hourly: Hourly,
    val latitude: Double,
    val longitude: Double,
    val timezone: String
)
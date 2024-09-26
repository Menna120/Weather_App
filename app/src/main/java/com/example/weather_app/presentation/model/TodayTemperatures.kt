package com.example.weather_app.presentation.model

data class TodayTemperatures(
    val date: String,
    val temperature: Map<String, List<Int>>
)

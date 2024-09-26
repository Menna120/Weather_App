package com.example.weather_app.presentation.screens

import com.example.weather_app.R
import com.example.weather_app.presentation.model.Precipitations
import com.example.weather_app.presentation.model.TodayTemperatures
import com.example.weather_app.presentation.model.WeatherMeasures
import com.example.weather_app.presentation.model.WeekForecast
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun generateFakeSuccessData(): WeatherUiState.Success {
    val currentTempIcon = R.drawable.ic_cloudy
    val todayTemperatures = generateFakeTodayTemperatures()
    val weekForecast = generateFakeWeekForecast()
    val weatherMeasures = WeatherMeasures(pressure = 1014, humidity = 30, windSpeed = 14)
    val precipitations = Precipitations(
        iconRes = R.drawable.ic_sunny,
        currentTemp = 28,
        weatherDesc = "Clear sky",
        maxTemp = 31,
        minTemp = 25
    )

    return WeatherUiState.Success(
        currentTempIcon = currentTempIcon,
        precipitations = precipitations,
        todayTemperatures = todayTemperatures,
        weatherMeasures = weatherMeasures,
        weekForecast = weekForecast
    )
}

fun generateFakeTodayTemperatures(): TodayTemperatures {
    val formatter = DateTimeFormatter.ofPattern("MMM, d")
    val currentDate = LocalDateTime.now().format(formatter)

    val fakeTemperatureData = mutableMapOf<String, List<Int>>()
    for (hour in 0..23) {
        val hourString = hour.toString().padStart(2, '0')
        val minuteString = "00"
        val timeString = "$hourString:$minuteString"
        val temperature = (15 + (hour % 12))
        val iconRes = when (hour) {
            in 6..18 -> R.drawable.ic_sunny
            else -> R.drawable.ic_cloudy
        }
        fakeTemperatureData[timeString] = listOf(temperature, iconRes)
    }

    return TodayTemperatures(
        date = currentDate,
        temperature = fakeTemperatureData
    )
}


fun generateFakeWeekForecast(): List<WeekForecast> {
    val formatter = DateTimeFormatter.ofPattern("EEEE")
    val currentDate = LocalDate.now()

    val weekForecast = mutableListOf<WeekForecast>()
    for (i in 0..6) {
        val dayOfWeek = currentDate.plusDays(i.toLong()).format(formatter)
        val maxTemp = (15 + i)
        val minTemp = (10 + i)
        val iconRes = when (i) {
            0 -> R.drawable.ic_sunny
            1 -> R.drawable.ic_cloudy
            2 -> R.drawable.ic_rainy
            3 -> R.drawable.ic_snowy
            4 -> R.drawable.ic_windy
            5 -> R.drawable.ic_sunnycloudy
            else -> R.drawable.ic_very_cloudy
        }
        weekForecast.add(WeekForecast(dayOfWeek, maxTemp, minTemp, iconRes))
    }

    return weekForecast
}
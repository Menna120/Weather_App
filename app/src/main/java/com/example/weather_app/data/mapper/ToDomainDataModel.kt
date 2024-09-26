package com.example.weather_app.data.mapper

import com.example.weather_app.data.remote.dto.Hourly
import com.example.weather_app.data.remote.dto.WeatherDto
import com.example.weather_app.domain.weather.WeatherData
import com.example.weather_app.domain.weather.WeatherInfo
import com.example.weather_app.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Hourly.toWeatherDataPerDay(): MutableMap<Int, MutableList<WeatherData>> {
    val weatherDataPerDay = mutableMapOf<Int, MutableList<WeatherData>>()
    time.forEachIndexed { index, time ->
        val data = createWeatherData(index, time)
        val dayIndex = index / 24
        weatherDataPerDay.getOrPut(dayIndex) { mutableListOf() }.add(data)
    }
    return weatherDataPerDay
}


private fun Hourly.createWeatherData(index: Int, time: String): WeatherData {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    return WeatherData(
        time = LocalDateTime.parse(time, formatter),
        temperatureCelsius = temperature[index],
        pressure = pressure[index],
        windSpeed = windSpeed[index],
        humidity = humidity[index].toDouble(),
        weatherType = WeatherType.fromWMO(weatherCode[index])
    )
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataPerDay = hourly.toWeatherDataPerDay()
    val currentWeatherData = weatherDataPerDay[0]?.find {
        val now = LocalDateTime.now()
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataPerDay,
        currentWeatherData = currentWeatherData
    )
}
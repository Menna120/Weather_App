package com.example.weather_app.domain.mapper

import com.example.weather_app.domain.weather.WeatherInfo
import com.example.weather_app.presentation.model.Precipitations
import com.example.weather_app.presentation.model.TodayTemperatures
import com.example.weather_app.presentation.model.WeatherMeasures
import com.example.weather_app.presentation.model.WeekForecast
import java.time.format.DateTimeFormatter

fun WeatherInfo.toPrecipitations(): Precipitations {
    return Precipitations(
        iconRes = currentWeatherData?.weatherType?.iconRes ?: 0,
        weatherDesc = currentWeatherData?.weatherType?.weatherDesc ?: "",
        currentTemp = currentWeatherData?.temperatureCelsius?.toInt() ?: 0,
        maxTemp = weatherDataPerDay[0]?.maxOf { it.temperatureCelsius }?.toInt() ?: 0,
        minTemp = weatherDataPerDay[0]?.minOf { it.temperatureCelsius }?.toInt() ?: 0,
    )
}

fun WeatherInfo.toTodayTemperatures(): TodayTemperatures {
    val dateFormatter = DateTimeFormatter.ofPattern("MMM, d")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val todayTemperatures = mutableMapOf<String, List<Int>>()
    weatherDataPerDay[0]?.forEach {
        val data = listOf(it.temperatureCelsius.toInt(), it.weatherType.iconRes)
        val index = it.time.format(timeFormatter)
        todayTemperatures[index] = data
    }
    return TodayTemperatures(
        date = currentWeatherData?.time?.format(dateFormatter) ?: "",
        temperature = todayTemperatures
    )
}

fun WeatherInfo.toWeatherMeasures(): WeatherMeasures {
    return WeatherMeasures(
        windSpeed = currentWeatherData?.windSpeed?.toInt() ?: 0,
        humidity = currentWeatherData?.humidity?.toInt() ?: 0,
        pressure = currentWeatherData?.pressure?.toInt() ?: 0,
    )
}

fun WeatherInfo.toWeekForecast(): List<WeekForecast> {
    val formatter = DateTimeFormatter.ofPattern("EEEE")
    return weatherDataPerDay.filter { it.key != 0 }
        .map { (_, data) ->
            val maxTemp = data.maxOf { it.temperatureCelsius }
            val minTemp = data.minOf { it.temperatureCelsius }
            val mostFrequentIcon =
                data.groupingBy { it.weatherType.iconRes }.eachCount().maxByOrNull { it.value }?.key
            WeekForecast(
                day = data[0].time.format(formatter),
                maxTemp = maxTemp.toInt(),
                minTemp = minTemp.toInt(),
                iconRes = mostFrequentIcon ?: 0
            )
        }
}
package com.example.weather_app.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data.location.ExceptionTitles
import com.example.weather_app.domain.location.LocationTracker
import com.example.weather_app.domain.mapper.toPrecipitations
import com.example.weather_app.domain.mapper.toTodayTemperatures
import com.example.weather_app.domain.mapper.toWeatherMeasures
import com.example.weather_app.domain.mapper.toWeekForecast
import com.example.weather_app.domain.repository.WeatherRepository
import com.example.weather_app.domain.util.RemoteResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun getWeatherInfo() {
        viewModelScope.launch {
            Log.i("WeatherApp", "Fetching weather info")
            _uiState.value = WeatherUiState.Loading
            try {
                val location = locationTracker.getCurrentLocation()
                if (location == null) {
                    _uiState.value = WeatherUiState.Error(ExceptionTitles.NO_INTERNET_CONNECTION)
                } else {
                    Log.d("WeatherApp", "Location: ${location.longitude}, ${location.latitude}")
                    val result = repository.getHourlyWeather(location.latitude, location.longitude)
                    when (result) {
                        is RemoteResponse.Success -> {
                            _uiState.value = result.data?.let { weatherData ->
                                WeatherUiState.Success(
                                    currentTempIcon = weatherData.currentWeatherData?.weatherType?.iconRes
                                        ?: 0,
                                    precipitations = weatherData.toPrecipitations(),
                                    todayTemperatures = weatherData.toTodayTemperatures(),
                                    weatherMeasures = weatherData.toWeatherMeasures(),
                                    weekForecast = weatherData.toWeekForecast()
                                )
                            } ?: WeatherUiState.Error("Data not available")
                        }

                        is RemoteResponse.Error -> {
                            Log.e("WeatherApp", "Error: ${result.message}")
                            _uiState.value = WeatherUiState.Error(result.message ?: "Error")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("WeatherApp", "Error: ${e.message}")
                _uiState.value = WeatherUiState.Error(e.message ?: "Error")
            }
        }
    }
}
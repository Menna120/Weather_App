package com.example.weather_app.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.domain.location.LocationTracker
import com.example.weather_app.domain.mapper.toPrecipitations
import com.example.weather_app.domain.mapper.toTodayTemperatures
import com.example.weather_app.domain.mapper.toWeatherMeasures
import com.example.weather_app.domain.mapper.toWeekForecast
import com.example.weather_app.domain.repository.WeatherRepository
import com.example.weather_app.domain.util.RemoteResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.onStart { getWeatherInfo() }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000L), WeatherUiState.Loading
    )

    fun getWeatherInfo() {
        viewModelScope.launch {
            Log.i("WeatherApp", "Fetching weather info")
            _uiState.value = WeatherUiState.Loading
            locationTracker.getCurrentLocation()?.let { location ->
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
                        _uiState.value = WeatherUiState.Error("Error: ${result.message}")
                    }
                }
            } ?: run {
                _uiState.value = WeatherUiState.Error("Location not available")
            }
        }
    }
}
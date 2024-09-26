package com.example.weather_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather_app.presentation.screens.WeatherScreen
import com.example.weather_app.presentation.screens.WeatherViewModel
import com.example.weather_app.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                val viewModel: WeatherViewModel = hiltViewModel()
                WeatherScreen(
                    weatherUiState = viewModel.uiState,
                    getWeatherInfo = viewModel::getWeatherInfo
                )
            }
        }
    }
}
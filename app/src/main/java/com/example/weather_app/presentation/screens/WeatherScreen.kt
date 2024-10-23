package com.example.weather_app.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather_app.data.location.EXCEPTIONS
import com.example.weather_app.presentation.screens.components.PrecipitationsItem
import com.example.weather_app.presentation.screens.components.TodayForecastCard
import com.example.weather_app.presentation.screens.components.WeatherCard
import com.example.weather_app.presentation.screens.components.WeekForecastCard
import com.example.weather_app.presentation.screens.utils.DayTheme
import com.example.weather_app.presentation.screens.utils.NightTheme
import com.example.weather_app.presentation.screens.utils.SUNRISE_HOUR
import com.example.weather_app.presentation.screens.utils.SUNSET_HOUR
import com.example.weather_app.theme.White
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalTime

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherUiState: StateFlow<WeatherUiState>,
    getWeatherInfo: () -> Unit,
) {
    val uiState = weatherUiState.collectAsStateWithLifecycle()
    val currentTime = LocalTime.now().hour
    val appTheme = if (currentTime in SUNRISE_HOUR..SUNSET_HOUR) DayTheme else NightTheme
    val gradient = Brush.linearGradient(colors = appTheme.backgroundGradient)

    LazyColumn(
        modifier = modifier
            .background(gradient)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    ) {
        when (uiState.value) {
            is WeatherUiState.Loading -> item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        color = White
                    )
                }
            }

            is WeatherUiState.Error -> {
                val exception = (uiState.value as WeatherUiState.Error).message
                Log.i("WeatherAppScreen", exception)
                item {
                    AlertDialog(
                        onDismissRequest = {},
                        title = { Text(exception) },
                        text = {
                            Text(
                                EXCEPTIONS[exception] ?: "Unknown error occurred"
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = { getWeatherInfo() },
                                colors = ButtonDefaults.buttonColors()
                                    .copy(containerColor = appTheme.cardBackgroundColor)
                            ) {
                                Text("Reload")
                            }
                        }
                    )
                }
            }

            is WeatherUiState.Success -> {
                item {
                    Icon(
                        imageVector = ImageVector.vectorResource((uiState.value as WeatherUiState.Success).currentTempIcon),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(100.dp),
                        tint = Color.Unspecified
                    )
                }
                item { PrecipitationsItem(precipitation = (uiState.value as WeatherUiState.Success).precipitations) }
                item {
                    WeatherCard(
                        weatherMeasures = (uiState.value as WeatherUiState.Success).weatherMeasures,
                        backgroundColor = appTheme.cardBackgroundColor
                    )
                }
                item {
                    TodayForecastCard(
                        todayTemperatures = (uiState.value as WeatherUiState.Success).todayTemperatures,
                        backgroundColor = appTheme.cardBackgroundColor
                    )
                }
                item {
                    WeekForecastCard(
                        weekForecasts = (uiState.value as WeatherUiState.Success).weekForecast,
                        backgroundColor = appTheme.cardBackgroundColor
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF645E5E)
@Composable
private fun WeatherScreenPreview() {
    WeatherScreen(
        weatherUiState = MutableStateFlow(generateFakeSuccessData())
    ) {}
}
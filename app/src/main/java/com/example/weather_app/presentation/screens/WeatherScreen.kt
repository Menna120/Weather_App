package com.example.weather_app.presentation.screens

import android.Manifest
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather_app.presentation.screens.components.PrecipitationsItem
import com.example.weather_app.presentation.screens.components.TodayForecastCard
import com.example.weather_app.presentation.screens.components.WeatherCard
import com.example.weather_app.presentation.screens.components.WeekForecastCard
import com.example.weather_app.theme.DarkBlue
import com.example.weather_app.theme.DayColor
import com.example.weather_app.theme.LightBlue
import com.example.weather_app.theme.NightColor
import com.example.weather_app.theme.Red
import com.example.weather_app.theme.White
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalTime

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen(
    weatherUiState: StateFlow<WeatherUiState>,
    modifier: Modifier = Modifier
) {
    val uiState = weatherUiState.collectAsStateWithLifecycle()
    val currentTime = LocalTime.now().hour
    val (colorList, cardColor) = if (currentTime in 6..18) DayColor to LightBlue else NightColor to DarkBlue
    val gradient = Brush.linearGradient(colors = colorList)
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    LaunchedEffect(true) { locationPermissions.launchMultiplePermissionRequest() }

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
                item {
                    Text(
                        text = (uiState.value as WeatherUiState.Error).message,
                        color = Red,
                        textAlign = TextAlign.Center
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
                        backgroundColor = cardColor
                    )
                }
                item {
                    TodayForecastCard(
                        todayTemperatures = (uiState.value as WeatherUiState.Success).todayTemperatures,
                        backgroundColor = cardColor
                    )
                }
                item {
                    WeekForecastCard(
                        weekForecasts = (uiState.value as WeatherUiState.Success).weekForecast,
                        backgroundColor = cardColor
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF645E5E)
@Composable
private fun WeatherScreenPreview() {
    WeatherScreen(weatherUiState = MutableStateFlow(generateFakeSuccessData()))
}
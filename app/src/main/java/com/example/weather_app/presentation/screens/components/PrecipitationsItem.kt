package com.example.weather_app.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_app.R
import com.example.weather_app.presentation.model.Precipitations
import com.example.weather_app.theme.White

@Composable
fun PrecipitationsItem(
    precipitation: Precipitations,
    modifier: Modifier = Modifier,
    color: Color = White,
    currentTempTextStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    precipitationTextStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${precipitation.currentTemp}°",
            color = color,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            style = currentTempTextStyle,
        )
        Text(text = precipitation.weatherDesc, color = color, style = precipitationTextStyle)
        Text(
            text = "Max: ${precipitation.maxTemp}°    Min: ${precipitation.minTemp}°",
            color = color,
            style = precipitationTextStyle
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PrecipitationsItemPreview() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF134CB5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PrecipitationsItem(
            precipitation = Precipitations(
                iconRes = R.drawable.ic_sunny,
                currentTemp = 28,
                weatherDesc = "Clear sky",
                maxTemp = 31,
                minTemp = 25
            )
        )
    }
}
package com.example.weather_app.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather_app.R
import com.example.weather_app.presentation.screens.generateFakeWeekForecast
import com.example.weather_app.presentation.model.WeekForecast
import com.example.weather_app.theme.DarkBlue
import com.example.weather_app.theme.White

@Composable
fun WeekForecastCard(
    weekForecasts: List<WeekForecast>,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color = White
) {
    BaseCard(
        backgroundColor = backgroundColor,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Next Forecast",
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_calendar),
                    contentDescription = "Calendar",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                weekForecasts.forEach {
                    ForecastItem(
                        day = it.day,
                        icon = it.iconRes,
                        temperatureHigh = it.maxTemp,
                        temperatureLow = it.minTemp
                    )
                }
            }
        }
    }
}

@Composable
fun ForecastItem(
    day: String,
    icon: Int,
    temperatureHigh: Int,
    temperatureLow: Int,
    color: Color = White
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = day,
            color = color,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = "Weather icon",
            modifier = Modifier.size(46.dp),
            tint = Color.Unspecified
        )
        Text(
            text = "$temperatureHigh°C / $temperatureLow°C",
            color = color,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WeekForecastCardPreview() {
    Column(Modifier.fillMaxSize()) {
        WeekForecastCard(
            weekForecasts = generateFakeWeekForecast(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            backgroundColor = DarkBlue
        )
    }

}
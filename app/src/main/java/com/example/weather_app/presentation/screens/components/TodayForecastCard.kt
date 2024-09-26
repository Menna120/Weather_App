package com.example.weather_app.presentation.screens.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather_app.presentation.screens.generateFakeTodayTemperatures
import com.example.weather_app.presentation.model.TodayTemperatures
import com.example.weather_app.theme.DarkBlue
import com.example.weather_app.theme.White

@Composable
fun TodayForecastCard(
    todayTemperatures: TodayTemperatures,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color = White
) {
    val contentHorizontalPadding = 16.dp
    BaseCard(
        backgroundColor = backgroundColor,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = contentHorizontalPadding),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Today",
                    modifier = Modifier.alignByBaseline(),
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = todayTemperatures.date,
                    modifier = Modifier.alignByBaseline(),
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = contentHorizontalPadding),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(todayTemperatures.temperature.entries.toList()) {
                    TemperatureForecast(
                        time = it.key,
                        temperature = it.value[0],
                        icon = it.value[1],
                        textColor = textColor
                    )
                }
            }
        }
    }
}

@Composable
fun TemperatureForecast(
    temperature: Int,
    time: String,
    @DrawableRes icon: Int,
    textColor: Color,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$temperature°C",
            color = textColor,
            fontWeight = FontWeight.Bold,
            style = textStyle
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = "Weather icon",
            modifier = Modifier.size(46.dp),
            tint = Color.Unspecified
        )
        Text(text = time, color = textColor, fontWeight = FontWeight.Bold, style = textStyle)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TodayForecastCardPreview() {
    Column(Modifier.fillMaxSize()) {
        TodayForecastCard(
            todayTemperatures = generateFakeTodayTemperatures(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            backgroundColor = DarkBlue,
        )
    }
}


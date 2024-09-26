package com.example.weather_app.presentation.screens.components

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather_app.R
import com.example.weather_app.presentation.model.WeatherMeasures
import com.example.weather_app.theme.DarkBlue
import com.example.weather_app.theme.White

@Composable
fun WeatherCard(
    weatherMeasures: WeatherMeasures,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    BaseCard(
        backgroundColor = backgroundColor,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            WeatherIcon(
                icon = R.drawable.ic_pressure,
                value = weatherMeasures.pressure,
                unit = "hpa"
            )
            WeatherIcon(icon = R.drawable.ic_humidity, value = weatherMeasures.humidity, unit = "%")
            WeatherIcon(icon = R.drawable.ic_wind, value = weatherMeasures.windSpeed, unit = "km/h")
        }
    }
}

@Composable
fun WeatherIcon(
    @DrawableRes icon: Int,
    value: Int,
    unit: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Humidity",
            modifier = modifier.size(28.dp),
            tint = Color.Unspecified
        )
        Text(text = "$value$unit", color = White, fontWeight = FontWeight.Bold, style = style)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WeatherCardPreview() {
    Column(Modifier.fillMaxSize()) {
        WeatherCard(
            weatherMeasures = WeatherMeasures(
                pressure = 1014,
                humidity = 30,
                windSpeed = 14
            ),
            backgroundColor = DarkBlue,
            modifier = Modifier.padding(16.dp)
        )
    }

}
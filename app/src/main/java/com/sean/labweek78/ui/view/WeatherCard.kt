package com.sean.labweek78.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sean.labweek78.R
import com.sean.labweek78.ui.model.weatherModel
import kotlin.math.roundToInt

private data class WeatherDetail(
    val icon: Int,
    val label: String,
    val value: String
)

@Composable
fun WeatherCard(weatherData: weatherModel) {
    val details = listOf(
        WeatherDetail(R.drawable.icon_humidity, "Humidity", "${weatherData.humidity}%"),
        WeatherDetail(R.drawable.icon_wind, "Wind", "${weatherData.windSpeed.roundToInt()} km/h"),
        WeatherDetail(R.drawable.icon_feels_like, "Feels Like", "${weatherData.feelsLike.roundToInt()}°"),
        WeatherDetail(R.drawable.vector_2, "Rainfall", "${weatherData.rainFall ?: 0.0} mm"),
        WeatherDetail(R.drawable.devices, "Pressure", "${weatherData.pressure} hPa"),
        WeatherDetail(R.drawable.cloud, "Clouds", "${weatherData.cloud}%")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(details) { detail ->
            DetailItem(
                icon = detail.icon,
                label = detail.label,
                value = detail.value
            )
        }
    }
}

@Composable
private fun DetailItem(icon: Int, label: String, value: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(text = value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = label, color = Color(0xffbbbbdd), fontSize = 12.sp)
        }
    }
}

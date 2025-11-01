package com.sean.labweek78.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun InfoCard(weatherData: weatherModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Sunrise Card
        InfoItem(
            modifier = Modifier.weight(1f),
            icon = R.drawable.vector,
            label = "Sunrise",
            value = formatTime(weatherData.sunriseTime)
        )
        // Sunset Card
        InfoItem(
            modifier = Modifier.weight(1f),
            icon = R.drawable.vector_21png,
            label = "Sunset",
            value = formatTime(weatherData.sunsetTime)
        )
    }
}

@Composable
private fun InfoItem(modifier: Modifier = Modifier, icon: Int, label: String, value: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(text = label, color = Color(0xffbbbbdd), fontSize = 14.sp)
                Text(text = value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Helper to convert Unix timestamp to a "h:mm a" time string
private fun formatTime(timestamp: Int): String {
    val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp.toLong() * 1000))
}

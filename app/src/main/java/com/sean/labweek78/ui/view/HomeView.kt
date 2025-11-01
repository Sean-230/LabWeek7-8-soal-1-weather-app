package com.sean.labweek78.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sean.labweek78.R
import com.sean.labweek78.ui.model.weatherModel
import com.sean.labweek78.ui.viewmodel.WeatherUiState
import com.sean.labweek78.ui.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun weatherAppView(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = viewModel()
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val weatherState by viewModel.weatherData.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.weather___home_2),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 40.dp)
        ) {
            // --- SEARCH BAR ROW ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { newText -> searchText = newText },
                    placeholder = { Text("Enter a City Name...", color = Color(0xffa0a5b7)) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .border(1.dp, Color.Gray, RoundedCornerShape(10.dp)),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            "Search",
                            tint = Color(0xffa0a5b7)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.3f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )

                Button(
                    onClick = {
                        if (searchText.isNotBlank()) {
                            viewModel.getWeather(searchText)
                        }
                    },
                    modifier = Modifier
                        .width(70.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.3f)
                    )
                ) {
                    Icon(
                        Icons.Default.Search,
                        "Search Icon",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // --- UI STATE HANDLING ---
            when (val state = uiState) {
                is WeatherUiState.Initial -> {
                    searchView()
                }
                is WeatherUiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                is WeatherUiState.Success -> {
                    val iconUrl = viewModel.getWeatherIconUrl(weatherState.icon)
                    weatherDetail(weatherState = weatherState, iconUrl = iconUrl)
                }
                is WeatherUiState.Error -> {
                    errorView()
                }
            }
        }
    }
}

@Composable
fun weatherDetail(
    weatherState: weatherModel,
    iconUrl: String
) {
    // Helper function to format date
    fun formatDate(timestamp: Int): String {
        val sdf = SimpleDateFormat("MMMM dd", Locale.getDefault())
        return sdf.format(Date(timestamp.toLong() * 1000))
    }

    // Helper function to format time
    fun formatTime(timestamp: Int): String {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp.toLong() * 1000))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp), // Reduced top padding
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // --- CITY AND DATE ---
            Text(text = weatherState.city, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(6.dp))
            Text(text = formatDate(weatherState.dateTime), color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(text = "Updated as of ${formatTime(weatherState.dateTime)}", color = Color(0xffbbbbdd), fontSize = 16.sp)
            Spacer(Modifier.height(20.dp))

            // --- MAIN WEATHER ICON AND TEMP ---
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(iconUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(80.dp)
            )
            Text(text = weatherState.weatherCondition, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(text = "${weatherState.temperature.roundToInt()}°C", color = Color.White, fontSize = 64.sp, fontWeight = FontWeight.Bold)

            // --- 2x3 WEATHER DETAILS GRID ---
            WeatherCard(weatherData = weatherState)

            // --- 1x2 SUNRISE/SUNSET INFO ---
            InfoCard(weatherData = weatherState)

            Spacer(Modifier.height(16.dp)) // Padding at the bottom
        }
    }
}


@Composable
fun searchView() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Placeholder",
            tint = Color(0xffa0a5b7),
            modifier = Modifier.size(90.dp)
        )
        Text(
            text = "Search for a city to get started",
            color = Color(0xffa0a5b7),
            fontSize = 16.sp
        )
    }
}

@Composable
fun errorView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Error Icon",
            modifier = Modifier.size(80.dp),
            tint = Color.Red.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "City not found or network error.",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun WeatherAppPreview() {
    weatherAppView()
}

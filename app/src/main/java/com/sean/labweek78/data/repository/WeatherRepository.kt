package com.sean.labweek78.data.repository

import com.sean.labweek78.data.service.WeatherApiService
import com.sean.labweek78.ui.model.weatherModel
import java.io.IOException

class WeatherRepository(private val service: WeatherApiService) {
    suspend fun getWeather(city: String): weatherModel {
        val response = service.getWeather(
            city = city,
            units = "metric",
            apiKey = "fc57802d07a9e6b8de869b0ff53d1873"
        )

        if (response.isSuccessful && response.body() != null) {
            val weathers = response.body()!!

            return weatherModel(
                city = weathers.name,
                dateTime = weathers.dt,
                updatedTime = weathers.dt.toString(),
                icon = weathers.weather[0].icon,
                temperature = weathers.main.temp,
                weatherCondition = weathers.weather[0].main,
                humidity = weathers.main.humidity,
                windSpeed = weathers.wind.speed,
                feelsLike = weathers.main.feels_like,
                rainFall = weathers.rain?.`1h` ?: 0.0,
                pressure = weathers.main.pressure,
                cloud = weathers.clouds.all,
                sunriseTime = weathers.sys.sunrise,
                sunsetTime = weathers.sys.sunset
            )
        } else {
            throw IOException("City not found or API error: ${response.message()}")
        }
    }

    fun getWeatherIconUrl(iconId: String): String {
        return "https://openweathermap.org/img/wn/$iconId@2x.png"
    }
}

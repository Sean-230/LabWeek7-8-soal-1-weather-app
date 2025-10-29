package com.sean.labweek78.data.repository

import com.sean.labweek78.data.service.WeatherApiService
import com.sean.labweek78.ui.model.weatherModel

class WeatherRepository(private val service: WeatherApiService) {
    suspend fun getWeather(city: String): weatherModel {
        val weathers = service.getWeather(
            city = city,
            units = "metric",
            apiKey = "fc57802d07a9e6b8de869b0ff53d1873"
        ).body()!!
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
    }

    fun getWeatherIconUrl(iconId: String): String {
        val url ="https://openweathermap.org/img/wn/$iconId@2x.png"
        return url
    }
}
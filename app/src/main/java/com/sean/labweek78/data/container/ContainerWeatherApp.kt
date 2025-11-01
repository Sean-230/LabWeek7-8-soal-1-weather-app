package com.sean.labweek78.data.container


import com.google.gson.GsonBuilder
import com.sean.labweek78.data.repository.WeatherRepository
import com.sean.labweek78.data.service.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

class AppContainer {
    companion object {
        val baseUrl = "https://api.openweathermap.org/"
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(baseUrl)
        .build()

    private val weatherService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(weatherService)
    }

}
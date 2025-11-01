package com.sean.labweek78.data.container


import com.google.gson.GsonBuilder
import com.sean.labweek78.data.repository.WeatherRepository
import com.sean.labweek78.data.service.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

class AppContainer {
    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"
    }

    private val gson = GsonBuilder()
        .serializeNulls() // This is the key change!
        .create()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson)) // Use the configured Gson
        .build()

    private val weatherService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(weatherService)
    }

}
package com.sean.labweek78.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.sean.labweek78.data.repository.WeatherRepository
import com.sean.labweek78.ui.model.weatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {
    private val _weather = MutableStateFlow<List<weatherModel>>(emptyList())
    val weather: StateFlow<List<weatherModel>> = _weather.asStateFlow()

    private val _weatherConditionIcon = MutableStateFlow<String?>(null)
    val weatherConditionIcon: StateFlow<String?> = _weatherConditionIcon.asStateFlow()


}
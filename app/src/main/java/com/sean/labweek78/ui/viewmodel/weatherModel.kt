package com.sean.labweek78.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class weatherModel: ViewModel() {
    private val _weather = MutableStateFlow<List<weatherModel>>(emptyList())
    val weather: StateFlow<List<weatherModel>> = _weather.asStateFlow()

    private val _weatherConditionIcon = MutableStateFlow<String?>(null)
    val weatherConditionIcon: StateFlow<String?> = _weatherConditionIcon.asStateFlow()

}
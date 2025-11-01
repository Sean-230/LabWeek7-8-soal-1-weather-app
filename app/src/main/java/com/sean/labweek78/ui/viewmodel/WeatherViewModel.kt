package com.sean.labweek78.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sean.labweek78.data.container.AppContainer
import com.sean.labweek78.data.repository.WeatherRepository
import com.sean.labweek78.ui.model.weatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

// Defines the possible states for the UI
sealed interface WeatherUiState {
    object Initial : WeatherUiState
    object Loading : WeatherUiState
    object Error : WeatherUiState
    object Success : WeatherUiState
}

class WeatherViewModel : ViewModel() {

    private val weatherRepository: WeatherRepository
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Initial)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()
    private val _weatherData = MutableStateFlow(weatherModel())
    val weatherData: StateFlow<weatherModel> = _weatherData.asStateFlow()

    init {
        weatherRepository = AppContainer().weatherRepository
    }
    fun getWeather(city: String) {
        _uiState.value = WeatherUiState.Loading
        viewModelScope.launch {
            try {
                val result = weatherRepository.getWeather(city)

                _weatherData.value = result

                _uiState.value = WeatherUiState.Success
            } catch (e: IOException) {
                _uiState.value = WeatherUiState.Error
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error
            }
        }
    }

    fun getWeatherIconUrl(iconId: String): String {
        return weatherRepository.getWeatherIconUrl(iconId)
    }
}

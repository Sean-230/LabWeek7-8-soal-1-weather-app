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

    // The repository for fetching weather data.
    private val weatherRepository: WeatherRepository

    // Private mutable state flow to hold the current UI state.
    // It's private to ensure that only the ViewModel can update it.
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Initial)
    // Public state flow that the UI can observe for changes.
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    // Private mutable state flow for the weather data model.
    private val _weatherData = MutableStateFlow(weatherModel())
    // Public, read-only state flow for the UI to collect weather data.
    val weatherData: StateFlow<weatherModel> = _weatherData.asStateFlow()

    init {
        // Initialize the repository using the AppContainer
        weatherRepository = AppContainer().weatherRepository
    }

    /**
     * Fetches weather data for the given city and updates the UI state accordingly.
     * @param city The name of the city to get weather for.
     */
    // in class WeatherViewModel

    fun getWeather(city: String) {
        _uiState.value = WeatherUiState.Loading
        viewModelScope.launch {
            try {
                // Fetch the complete weather data from the repository
                val result = weatherRepository.getWeather(city)

                // Update the state with all the new data
                _weatherData.value = result

                // Set state to Success
                _uiState.value = WeatherUiState.Success
            } catch (e: IOException) {
                _uiState.value = WeatherUiState.Error // Network errors
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error // Other errors (e.g., city not found)
            }
        }
    }


    /**
     * Returns the full URL for a given weather icon ID.
     * @param iconId The ID of the weather icon.
     * @return The complete URL string for the icon image.
     */
    fun getWeatherIconUrl(iconId: String): String {
        return weatherRepository.getWeatherIconUrl(iconId)
    }
}

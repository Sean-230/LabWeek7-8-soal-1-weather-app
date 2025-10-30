package com.sean.labweek78

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.sean.labweek78.data.container.AppContainer
import com.sean.labweek78.ui.theme.LabWeek78Theme
import com.sean.labweek78.ui.view.weatherAppView
import com.sean.labweek78.ui.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    private val appContainer = AppContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabWeek78Theme {
                val viewModel = WeatherViewModel(appContainer.weatherRepository)
                weatherAppView(
                    viewModel = viewModel,
                    modifier = Modifier.padding()
                )
            }
        }
    }
}
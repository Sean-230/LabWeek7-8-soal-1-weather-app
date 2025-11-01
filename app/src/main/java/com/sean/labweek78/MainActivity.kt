package com.sean.labweek78

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sean.labweek78.ui.theme.LabWeek78Theme
import com.sean.labweek78.ui.view.weatherAppView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabWeek78Theme {
                weatherAppView()
            }
        }
    }
}
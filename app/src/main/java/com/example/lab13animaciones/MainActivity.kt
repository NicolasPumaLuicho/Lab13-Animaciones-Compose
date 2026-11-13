package com.example.lab13animaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lab13animaciones.ui.Lab13App
import com.example.lab13animaciones.ui.theme.Lab13Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab13Theme {
                Lab13App()
            }
        }
    }
}

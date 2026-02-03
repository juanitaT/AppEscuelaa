package com.example.appsicenet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appsicenet.di.DefaultAppContainer
import com.example.appsicenet.ui.theme.AppSicenetTheme
import com.example.appsicenet.ui.theme.Screens.HomeScreen
import com.example.appsicenet.ui.theme.Screens.PerfilScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppSicenetTheme {
                var pantallaActual by remember { mutableStateOf("login") }
                val appContainer = DefaultAppContainer(applicationContext)
                when (pantallaActual) {
                    "login" -> {
                        HomeScreen(
                            onLoginSuccess = { pantallaActual = "perfil" },
                            snRepository = appContainer.snRepository
                        )
                    }

                    "perfil" -> {
                        PerfilScreen(
                            snRepository = appContainer.snRepository
                        )
                    }
                }
            }
        }
    }
}
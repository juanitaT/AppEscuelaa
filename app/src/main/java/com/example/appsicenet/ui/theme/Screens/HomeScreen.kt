package com.example.appsicenet.ui.theme.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appsicenet.ViewModel.LoginViewModel
import com.example.appsicenet.ViewModel.LoginViewModelFactory
import com.example.appsicenet.ViewModel.PerfilViewModel
import com.example.appsicenet.datos.repository.SNRepository

@Composable
fun HomeScreen(
    onLoginSuccess: () -> Unit,
    snRepository: SNRepository
) {
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(snRepository)
    )

    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "SICENET",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.login(
                    usuario = usuario,
                    password = password,
                    onSuccess = onLoginSuccess,
                    onError = { mensaje ->
                        Log.e("SICENET_LOGIN", mensaje)
                    }
                )
            }
        ) {
            Text("Iniciar sesión")
        }
    }
}
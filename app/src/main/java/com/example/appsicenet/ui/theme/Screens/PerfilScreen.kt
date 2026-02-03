package com.example.appsicenet.ui.theme.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appsicenet.ViewModel.PerfilViewModel
import com.example.appsicenet.ViewModel.PerfilViewModelFactory
import com.example.appsicenet.datos.repository.SNRepository

@Composable
fun PerfilScreen(
    snRepository: SNRepository,
    viewModel: PerfilViewModel = viewModel(
        factory = PerfilViewModelFactory(snRepository)
    )
) {
    LaunchedEffect(Unit) {
        viewModel.cargarPerfil()
    }

    val perfil = viewModel.perfil

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        perfil?.let {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Perfil del Alumno",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Divider()

                    PerfilItem("Nombre", it.nombre)
                    PerfilItem("Matrícula", it.matricula)
                    PerfilItem("Carrera", it.carrera)
                    PerfilItem("Especialidad", it.especialidad)
                    PerfilItem("Semestre actual", it.semActual.toString())
                    PerfilItem("Créditos acumulados", it.cdtosAcumulados.toString())
                    PerfilItem("Estatus", it.estatus)
                }
            }

        } ?: CircularProgressIndicator()
    }
}
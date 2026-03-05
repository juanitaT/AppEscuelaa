package com.example.appsicenet.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appsicenet.SessionManager.matricula
import com.example.appsicenet.ViewModel.PerfilViewModel
import com.example.appsicenet.ViewModel.PerfilViewModelFactory
import com.example.appsicenet.datos.repository.LocalSNRepository
import com.example.appsicenet.datos.repository.SNRepository

@Composable
fun PerfilScreen(
    snRepository: SNRepository,
    localRepository: LocalSNRepository,
) {
    val context = LocalContext.current
    val viewModel: PerfilViewModel = viewModel(
        factory = PerfilViewModelFactory(
            localRepository = localRepository,
            context = context
        )
    )

    LaunchedEffect(matricula) {
        viewModel.verificarYSincronizarPerfil(matricula.uppercase())
    }

    val perfilFlow = remember(matricula) {
        viewModel.obtenerPerfil(matricula.uppercase())
    }

    val perfil by perfilFlow.collectAsState(initial = null)

    // Colores verde personalizados
    val greenPrimary = Color(0xFF2E7D32)
    val greenLight = Color(0xFF4CAF50)
    val greenDark = Color(0xFF1B5E20)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        greenDark,
                        greenPrimary,
                        greenLight
                    )
                )
            )
    ) {
        if (perfil == null) {
            // Estado de carga
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Cargando perfil...",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            val p = perfil!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // Título de bienvenida
                Text(
                    text = "Mi Perfil",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 36.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Tarjeta principal con información del usuario
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp)
                    ) {
                        // Nombre
                        Text(
                            text = "Nombre completo",
                            fontSize = 14.sp,
                            color = greenPrimary.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = p.nombre,
                            fontSize = 24.sp,
                            color = greenPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Divider(
                            color = greenLight.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Matrícula
                        Text(
                            text = "Matrícula",
                            fontSize = 14.sp,
                            color = greenPrimary.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = p.matricula,
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Divider(
                            color = greenLight.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Carrera
                        Text(
                            text = "Carrera",
                            fontSize = 14.sp,
                            color = greenPrimary.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = p.carrera,
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Divider(
                            color = greenLight.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Especialidad
                        Text(
                            text = "Especialidad",
                            fontSize = 14.sp,
                            color = greenPrimary.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = p.especialidad,
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Divider(
                            color = greenLight.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Semestre actual
                        Text(
                            text = "Semestre actual",
                            fontSize = 14.sp,
                            color = greenPrimary.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = p.semActual.toString(),
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Divider(
                            color = greenLight.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Créditos acumulados
                        Text(
                            text = "Créditos acumulados",
                            fontSize = 14.sp,
                            color = greenPrimary.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = p.cdtosAcumulados.toString(),
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Divider(
                            color = greenLight.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Estatus
                        Text(
                            text = "Estatus académico",
                            fontSize = 14.sp,
                            color = greenPrimary.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = p.estatus,
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
package com.example.appsicenet.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appsicenet.ViewModel.CargaAcademicaViewModel
import com.example.appsicenet.ViewModel.CargaAcademicaViewModelFactory
import com.example.appsicenet.datos.repository.LocalSNRepository
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CargaAcademicaScreen(
    localRepository: LocalSNRepository
) {
    val context = LocalContext.current
    val viewModel: CargaAcademicaViewModel = viewModel(
        factory = CargaAcademicaViewModelFactory(
            localRepository = localRepository,
            context = context
        )
    )

    LaunchedEffect(Unit) {
        viewModel.verificarYSincronizar()
    }

    val carga by viewModel.carga.collectAsState()
    val ultimaActualizacion by viewModel.ultimaActualizacion.collectAsState()

    // Colores azules simples
    val bluePrimary = Color(0xFF1976D2)
    val blueLight = Color(0xFFE3F2FD)
    val backgroundColor = Color(0xFFF5F9FF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Header simple
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(bluePrimary)
                .padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Mi Carga Académica",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                ultimaActualizacion?.let {
                    val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    Text(
                        text = "Actualizado: ${fmt.format(Date(it))}",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // Contenido
        if (carga.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = bluePrimary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Cargando...", color = bluePrimary)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                // Resumen rápido
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = blueLight)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            val totalCreditos = carga.sumOf { it.creditos }

                            ResumenItem(numero = "${carga.size}", etiqueta = "Materias")
                            ResumenItem(numero = "$totalCreditos", etiqueta = "Créditos")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Lista de materias
                items(carga) { materia ->
                    TarjetaMateriaSimple(materia)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ResumenItem(numero: String, etiqueta: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = numero,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2)
        )
        Text(
            text = etiqueta,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun TarjetaMateriaSimple(materia: com.example.appsicenet.datos.local.entity.CargaAcademicaEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Nombre de la materia
            Text(
                text = materia.nombreMateria,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Detalles en fila
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetalleItem(icono = Icons.Default.Group, texto = "Grupo: ${materia.grupo}")
                DetalleItem(icono = Icons.Default.Star, texto = "${materia.creditos} créditos")
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Docente
            DetalleItem(icono = Icons.Default.Person, texto = "Docente: ${materia.docente}")

            Spacer(modifier = Modifier.height(4.dp))

            // Horario
            DetalleItem(icono = Icons.Default.Schedule, texto = "Horario: ${materia.horario}")
        }
    }
}

@Composable
fun DetalleItem(icono: androidx.compose.ui.graphics.vector.ImageVector, texto: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = Color(0xFF1976D2),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = texto,
            fontSize = 12.sp,
            color = Color.DarkGray
        )
    }
}
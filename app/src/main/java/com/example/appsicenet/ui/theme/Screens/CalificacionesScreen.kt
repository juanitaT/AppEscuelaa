package com.example.appsicenet.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appsicenet.ViewModel.CalificacionesViewModel
import com.example.appsicenet.ViewModel.CalificacionesViewModelFactory
import com.example.appsicenet.datos.local.entity.CalificacionFinalEntity
import com.example.appsicenet.datos.local.entity.CalificacionUnidadEntity
import com.example.appsicenet.datos.repository.LocalSNRepository

// ─── Paleta de colores ────────────────────────────────────────
private val GreenPrimary = Color(0xFF2E7D32)
private val GreenLight   = Color(0xFF4CAF50)
private val GreenDark    = Color(0xFF1B5E20)
private val GreenSurface = Color(0xFFF1F8F1)

@Composable
fun CalificacionesScreen(
    localRepository: LocalSNRepository
) {
    val viewModel: CalificacionesViewModel = viewModel(
        factory = CalificacionesViewModelFactory(localRepository)
    )

    val finales by viewModel.calificacionesFinales.collectAsState()
    val unidades by viewModel.calificacionesUnidad.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenSurface)
    ) {
        if (finales.isEmpty()) {
            // ── Estado de carga ───────────────────────────────────────
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = GreenPrimary,
                    modifier = Modifier.size(56.dp),
                    strokeWidth = 4.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Cargando calificaciones...",
                    color = GreenPrimary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                // ── Header con gradiente ─────────────────────────────
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(GreenDark, GreenPrimary, GreenLight)
                                ),
                                shape = RoundedCornerShape(
                                    bottomStart = 28.dp,
                                    bottomEnd = 28.dp
                                )
                            )
                            .padding(top = 36.dp, bottom = 28.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = " Reporte de Calificaciones",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "${finales.size} materias registradas",
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.80f)
                            )

                            // Chips de resumen
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                val aprobadas  = finales.count { it.calificacionFinal >= 70 }
                                val reprobadas = finales.count { it.calificacionFinal in 1..69 }
                                val pendientes = finales.count { it.calificacionFinal == 0 }
                                CalHeaderChip(" Aprobo", "$aprobadas")
                                CalHeaderChip(" Reprobo.", "$reprobadas")
                                CalHeaderChip(" Pendiente.", "$pendientes")
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // ── Filas de materias ────────────────────────────────
                items(finales) { materiaFinal ->
                    val unidadesMateria = unidades.filter {
                        it.materia.equals(materiaFinal.materia, ignoreCase = true)
                    }
                    CalificacionCard(materiaFinal, unidadesMateria)
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

// ─── Chip de resumen en el header ────────────────────────────
@Composable
private fun CalHeaderChip(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 22.sp
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.85f)
        )
    }
}

// ─── Tarjeta de calificación por materia ─────────────────────
@Composable
fun CalificacionCard(
    materiaFinal: CalificacionFinalEntity,
    unidades: List<CalificacionUnidadEntity>
) {
    val calFinal = materiaFinal.calificacionFinal

    val (scoreColor, scoreBg) = when {
        calFinal >= 80 -> Pair(Color(0xFF1B5E20), Color(0xFFE8F5E9))
        calFinal >= 70 -> Pair(Color(0xFF2E7D32), Color(0xFFF1F8F1))
        calFinal >  0  -> Pair(Color(0xFFC62828), Color(0xFFFFEBEE))
        else           -> Pair(Color.Gray,         Color(0xFFF5F5F5))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Nombre de la materia
                Text(
                    text = materiaFinal.materia,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF1A1A1A),
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.width(12.dp))

                // Círculo con calificación final
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(scoreBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (calFinal == 0) "—" else "$calFinal",
                        fontWeight = FontWeight.ExtraBold,
                        color = scoreColor,
                        fontSize = 16.sp
                    )
                }
            }

            // Badges de unidades
            if (unidades.isNotEmpty() && !(unidades.size == 1 && unidades[0].calificacion == 0)) {
                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = Color(0xFFE0E0E0), thickness = 0.8.dp)
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Unidades:",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        unidades.take(5).forEach { unidad ->
                            val uColor = if (unidad.calificacion >= 70)
                                Color(0xFF2E7D32) else Color(0xFFC62828)
                            val uBg = if (unidad.calificacion >= 70)
                                Color(0xFFE8F5E9) else Color(0xFFFFEBEE)

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(uBg)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "U${unidad.unidad}: ${unidad.calificacion}",
                                    fontSize = 11.sp,
                                    color = uColor,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        if (unidades.size > 5) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFF5F5F5))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    "+${unidades.size - 5}",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = " Calificaciones de unidades pendientes",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
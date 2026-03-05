package com.example.appsicenet.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appsicenet.ViewModel.CardexViewModel
import com.example.appsicenet.ViewModel.CardexViewModelFactory
import com.example.appsicenet.datos.repository.LocalSNRepository

import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CardexScreen(
    localRepository: LocalSNRepository
) {
    val context = LocalContext.current

    val viewModel: CardexViewModel = viewModel<CardexViewModel>(
        factory = CardexViewModelFactory(
            localRepository = localRepository,
            context = context
        )
    )

    LaunchedEffect(Unit) {
        viewModel.verificarYSincronizar()
    }

    val cardex by viewModel.cardex.collectAsState()
    val ultimaActualizacion by viewModel.ultimaActualizacion.collectAsState()

    val greenPrimary = Color(0xFF2E7D32)
    val greenLight  = Color(0xFF4CAF50)
    val greenDark   = Color(0xFF1B5E20)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F8F1))
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            // ── Header con gradiente ─────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(greenDark, greenPrimary, greenLight)
                            ),
                            shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                        )
                        .padding(top = 36.dp, bottom = 28.dp, start = 24.dp, end = 24.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "📋 Kárdex Académico",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        ultimaActualizacion?.let {
                            val fmt = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                            Text(
                                text = "Actualizado: ${fmt.format(Date(it))}",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.80f),
                                textAlign = TextAlign.Center
                            )
                        }

                        // Resumen rápido
                        if (cardex.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                val aprobadas = cardex.count {
                                    it.acreditado.equals("si", ignoreCase = true) ||
                                            it.acreditado.equals("sí", ignoreCase = true) ||
                                            it.acreditado == "1"
                                }
                                val reprobadas = cardex.size - aprobadas
                                StatChip("Total", "${cardex.size}", Color.White)
                                StatChip("Aprobadas", "$aprobadas", Color(0xFFA5D6A7))
                                StatChip("No aprobadas", "$reprobadas", Color(0xFFEF9A9A))
                            }
                        }
                    }
                }
            }

            // ── Contenido ──────────────────────────────────────────────
            item { Spacer(modifier = Modifier.height(16.dp)) }

            if (cardex.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxSize()
                            .padding(top = 80.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                color = greenPrimary,
                                modifier = Modifier.size(56.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Cargando kárdex...",
                                color = greenPrimary,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            } else {
                items(cardex) { materia ->
                    CardexItem(materia)
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
private fun StatChip(label: String, value: String, chipColor: Color) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = chipColor.copy(alpha = 0.25f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 20.sp)
            Text(label, color = Color.White.copy(alpha = 0.85f), fontSize = 11.sp)
        }
    }
}
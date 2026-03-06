package com.example.appsicenet.ui.theme.Screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
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
import com.example.appsicenet.datos.local.entity.CardexEntity
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

    // ─── Colores AZULES ────────────────────────────────────────────────
    val bluePrimary = Color(0xFF1976D2)
    val blueLight   = Color(0xFF42A5F5)
    val blueDark    = Color(0xFF0D47A1)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            // ── Header ───────────────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(blueDark, bluePrimary, blueLight),
                                start = Offset(0f, 0f),
                                end = Offset(Float.POSITIVE_INFINITY, 300f)
                            ),
                            shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        )
                ) {
                    // Círculo decorativo superior izquierdo
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .offset(x = (-40).dp, y = (-40).dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(Color.White.copy(alpha = 0.10f), Color.Transparent)
                                ),
                                shape = CircleShape
                            )
                    )
                    // Círculo decorativo inferior derecho
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.BottomEnd)
                            .offset(x = 30.dp, y = 30.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(blueLight.copy(alpha = 0.20f), Color.Transparent)
                                ),
                                shape = CircleShape
                            )
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 44.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Text(
                            text = "Kárdex Académico",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.3.sp
                        )

                        ultimaActualizacion?.let {
                            Spacer(modifier = Modifier.height(6.dp))
                            val fmt = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color.White.copy(alpha = 0.12f),
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                    .padding(horizontal = 14.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Actualizado: ${fmt.format(Date(it))}",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.90f),
                                    letterSpacing = 0.3.sp
                                )
                            }
                        }

                        if (cardex.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(20.dp))
                            StatChip("Total cursadas", "${cardex.size}", Color.White)
                        }


                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

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
                                color = bluePrimary,
                                strokeWidth = 3.dp,
                                modifier = Modifier.size(52.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Cargando kárdex...",
                                color = bluePrimary,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp,
                                letterSpacing = 0.3.sp
                            )
                        }
                    }
                }
            } else {
                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(16.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(bluePrimary, blueLight)
                                    ),
                                    shape = RoundedCornerShape(2.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "MATERIAS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = bluePrimary.copy(alpha = 0.7f),
                            letterSpacing = 2.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                itemsIndexed(cardex) { index, materia ->
                    AnimatedCardexItem(materia = materia, index = index)
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

@Composable
private fun AnimatedCardexItem(materia: CardexEntity, index: Int) {
    val alpha = remember { Animatable(0f) }
    val offsetX = remember { Animatable(30f) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(index * 50L)
        alpha.animateTo(1f, animationSpec = tween(400))
        offsetX.animateTo(0f, animationSpec = tween(400))
    }

    Box(
        modifier = Modifier
            .alpha(alpha.value)
            .offset(x = offsetX.value.dp)
    ) {
        CardexItemCard(materia)
    }
}

@Composable
private fun CardexItemCard(materia: CardexEntity) {
    val bluePrimary = Color(0xFF1976D2)
    val blueLight   = Color(0xFF42A5F5)

    val acreditado = materia.acreditado.equals("si", ignoreCase = true) ||
            materia.acreditado.equals("sí", ignoreCase = true) ||
            materia.acreditado == "1"

    val (statusColor, statusBg, statusText) = if (acreditado) {
        Triple(bluePrimary, Color(0xFFE3F2FD), "ACREDITADA")
    } else {
        Triple(Color(0xFFC62828), Color(0xFFFFEBEE), "NO ACREDITADA")
    }

    val calColor = if (materia.calificacion >= 70) bluePrimary else Color(0xFFC62828)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Barra lateral de color
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(52.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(statusColor, statusColor.copy(alpha = 0.3f))
                        ),
                        shape = RoundedCornerShape(2.dp)
                    )
            )

            Spacer(modifier = Modifier.width(14.dp))

            // Info central
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = materia.materia,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF1A1A2E),
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Badge periodo
                Box(
                    modifier = Modifier
                        .background(Color(0xFFF0F4FF), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = materia.periodo,
                        fontSize = 10.sp,
                        color = bluePrimary.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Calificación grande
            Text(
                text = if (materia.calificacion == 0) "—" else "${materia.calificacion}",
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                color = calColor,
                letterSpacing = (-1).sp
            )
        }
    }
}

@Composable
private fun StatChip(label: String, value: String, chipColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = chipColor.copy(alpha = 0.18f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 18.dp, vertical = 12.dp)
    ) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label.uppercase(),
            color = Color.White.copy(alpha = 0.80f),
            fontSize = 10.sp,
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
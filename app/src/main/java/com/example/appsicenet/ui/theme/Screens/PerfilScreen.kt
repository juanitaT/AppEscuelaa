package com.example.appsicenet.ui.theme.Screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appsicenet.ViewModel.PerfilViewModel
import com.example.appsicenet.ViewModel.PerfilViewModelFactory
import com.example.appsicenet.datos.repository.LocalSNRepository

@Composable
fun PerfilScreen(
    matricula: String,
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

    // ─── Colores AZULES ────────────────────────────────────────────────
    val bluePrimary = Color(0xFF1976D2)
    val blueLight = Color(0xFF42A5F5)
    val blueDark = Color(0xFF0D47A1)
    val blueBackground = Color(0xFF1565C0)

    // Animación de entrada
    val alphaAnim = remember { Animatable(0f) }
    val offsetAnim = remember { Animatable(40f) }

    LaunchedEffect(perfil) {
        if (perfil != null) {
            alphaAnim.animateTo(1f, animationSpec = tween(600))
            offsetAnim.animateTo(0f, animationSpec = tween(600))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        blueDark,
                        blueBackground,
                        bluePrimary
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(400f, 1200f)
                )
            )
    ) {
        // Decoración: círculo superior difuminado
        Box(
            modifier = Modifier
                .size(280.dp)
                .offset(x = (-60).dp, y = (-60).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            blueLight.copy(alpha = 0.25f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Decoración: círculo inferior derecho
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 60.dp, y = 60.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            blueLight.copy(alpha = 0.18f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        if (perfil == null) {
            // ─── Estado de carga ──────────────────────────────────────
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Cargando perfil...",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
            }
        } else {
            val p = perfil!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .alpha(alphaAnim.value)
                    .offset(y = offsetAnim.value.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(56.dp))

                // ─── Avatar con inicial ───────────────────────────────
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(90.dp)
                        .shadow(16.dp, CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(blueLight, bluePrimary)
                            ),
                            shape = CircleShape
                        )
                ) {
                    Text(
                        text = p.nombre.firstOrNull()?.uppercase() ?: "?",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ─── Nombre principal ─────────────────────────────────
                Text(
                    text = p.nombre,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // ─── Badge de estatus ─────────────────────────────────
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(50.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = p.estatus,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White.copy(alpha = 0.9f),
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ─── Tarjeta: Datos académicos ────────────────────────
                GlassCard {
                    Column(modifier = Modifier.padding(24.dp)) {
                        SectionHeader(title = "Datos Académicos", accent = blueLight)

                        Spacer(modifier = Modifier.height(16.dp))

                        InfoRow(label = "Matrícula", value = p.matricula, accent = bluePrimary)
                        InfoDivider(blueLight)
                        InfoRow(label = "Carrera", value = p.carrera, accent = bluePrimary)
                        InfoDivider(blueLight)
                        InfoRow(label = "Especialidad", value = p.especialidad, accent = bluePrimary)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ─── Tarjeta: Progreso ────────────────────────────────
                GlassCard {
                    Column(modifier = Modifier.padding(24.dp)) {
                        SectionHeader(title = "Progreso", accent = blueLight)

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatChip(
                                value = p.semActual.toString(),
                                label = "Semestre",
                                accent = blueLight,
                                bluePrimary = bluePrimary
                            )
                            // Separador vertical
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(64.dp)
                                    .background(blueLight.copy(alpha = 0.3f))
                            )
                            StatChip(
                                value = p.cdtosAcumulados.toString(),
                                label = "Créditos",
                                accent = blueLight,
                                bluePrimary = bluePrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

// ─── Componentes auxiliares ────────────────────────────────────────────────────

@Composable
private fun GlassCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        )
    ) {
        content()
    }
}

@Composable
private fun SectionHeader(title: String, accent: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(20.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(accent, accent.copy(alpha = 0.4f))
                    ),
                    shape = RoundedCornerShape(2.dp)
                )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = accent,
            letterSpacing = 1.2.sp
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String, accent: Color) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(
            text = label.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = accent.copy(alpha = 0.6f),
            letterSpacing = 1.5.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1A1A2E),
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun InfoDivider(color: Color) {
    Divider(
        color = color.copy(alpha = 0.2f),
        thickness = 1.dp
    )
}

@Composable
private fun StatChip(value: String, label: String, accent: Color, bluePrimary: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = bluePrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = accent.copy(alpha = 0.7f),
            letterSpacing = 1.5.sp
        )
    }
}
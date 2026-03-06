package com.example.appsicenet.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.WorkManager
import com.example.appsicenet.ViewModel.CalificacionesViewModel
import com.example.appsicenet.ViewModel.CalificacionesViewModelFactory
import com.example.appsicenet.datos.local.entity.CalificacionFinalEntity
import com.example.appsicenet.datos.local.entity.CalificacionUnidadEntity
import com.example.appsicenet.datos.repository.LocalSNRepository

// ─── Paleta de colores AZULES ────────────────────────────────────────
private val BluePrimary  = Color(0xFF1976D2)
private val BlueLight    = Color(0xFF42A5F5)
private val BlueDark     = Color(0xFF0D47A1)
private val BlueSurface  = Color(0xFFE3F2FD)
private val BlueHeader   = Color(0xFF1565C0)
private val BlueBorder   = Color(0xFF90CAF9)
private val BlueRowEven  = Color(0xFFFFFFFF)
private val BlueRowOdd   = Color(0xFFF0F7FF)

// Número máximo de unidades a mostrar como columnas
private const val MAX_UNIDADES = 8

@Composable
fun CalificacionesScreen(
    localRepository: LocalSNRepository
) {
    val context = LocalContext.current

    val viewModel: CalificacionesViewModel = viewModel(
        factory = CalificacionesViewModelFactory(
            localRepository,
            WorkManager.getInstance(context)
        )
    )

    LaunchedEffect(Unit) {
        viewModel.verificarYSincronizar()
    }

    val finales  by viewModel.calificacionesFinales.collectAsState()
    val unidades by viewModel.calificacionesUnidad.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueSurface)
    ) {
        if (finales.isEmpty()) {
            // ── Estado de carga ───────────────────────────────────────
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = BluePrimary,
                    modifier = Modifier.size(52.dp),
                    strokeWidth = 3.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Cargando calificaciones...",
                    color = BluePrimary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            }
        } else {
            val aprobadas  = finales.count { it.calificacionFinal >= 70 }
            val reprobadas = finales.count { it.calificacionFinal in 1..69 }
            val pendientes = finales.count { it.calificacionFinal == 0 }

            LazyColumn(modifier = Modifier.fillMaxSize()) {

                // ── Header ────────────────────────────────────────────
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(BlueDark, BluePrimary, BlueLight),
                                    start = Offset(0f, 0f),
                                    end = Offset(Float.POSITIVE_INFINITY, 200f)
                                )
                            )
                            .padding(top = 40.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Calificaciones",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                letterSpacing = (-0.5).sp
                            )
                            Text(
                                text = "Unidades",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.80f),
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Chips de resumen
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                SummaryChip("${finales.size}", "TOTAL",     Color.White)
                                SummaryChip("$aprobadas",     "APROBADAS",  Color(0xFFBBDEFB))
                                SummaryChip("$reprobadas",    "REPROBADAS", Color(0xFFEF9A9A))
                                SummaryChip("$pendientes",    "PENDIENTES", Color(0xFFFFF9C4))
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // ── Tabla con scroll horizontal ───────────────────────
                item {
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, BlueBorder, RoundedCornerShape(12.dp))
                    ) {
                        // Encabezado de la tabla
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BlueHeader)
                                .horizontalScroll(scrollState)
                        ) {
                            TableHeaderCell(text = "MATERIA", width = 160, isFirst = true)
                            TableHeaderCell(text = "FINAL",   width = 54)
                            for (u in 1..MAX_UNIDADES) {
                                TableHeaderCell(text = "U#$u", width = 46)
                            }
                        }

                        // Filas de materias
                        finales.forEachIndexed { index, materiaFinal ->
                            val unidadesMateria = unidades
                                .filter { it.materia.equals(materiaFinal.materia, ignoreCase = true) }
                                .sortedBy { it.unidad }

                            val bgColor = if (index % 2 == 0) BlueRowEven else BlueRowOdd
                            val isLast  = index == finales.lastIndex

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(bgColor)
                                    .then(
                                        if (!isLast) Modifier.border(
                                            width = 0.5.dp,
                                            color = BlueBorder.copy(alpha = 0.4f),
                                            shape = RoundedCornerShape(0.dp)
                                        ) else Modifier
                                    )
                                    .horizontalScroll(scrollState),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Nombre materia
                                TableDataCell(
                                    text = materiaFinal.materia,
                                    width = 160,
                                    isFirst = true,
                                    isBold = true,
                                    textColor = Color(0xFF1A1A2E)
                                )

                                // Calificación final
                                val calFinal = materiaFinal.calificacionFinal
                                val finalColor = when {
                                    calFinal >= 70 -> BluePrimary
                                    calFinal > 0   -> Color(0xFFC62828)
                                    else           -> Color(0xFF9E9E9E)
                                }
                                TableDataCell(
                                    text = if (calFinal == 0) "—" else "$calFinal",
                                    width = 54,
                                    textColor = finalColor,
                                    isBold = true
                                )

                                // Columnas de unidades U1..U8
                                for (u in 1..MAX_UNIDADES) {
                                    val unidadEntity = unidadesMateria.find { it.unidad == u }
                                    val cal = unidadEntity?.calificacion ?: -1
                                    val (calText, calColor) = when {
                                        cal > 0 && cal >= 70 -> Pair("$cal", BluePrimary)
                                        cal > 0              -> Pair("$cal", Color(0xFFC62828))
                                        cal == 0             -> Pair("0",   Color(0xFF9E9E9E))
                                        else                 -> Pair("",    Color.Transparent)
                                    }
                                    TableDataCell(
                                        text = calText,
                                        width = 46,
                                        textColor = calColor
                                    )
                                }
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

// ─── Celda de encabezado ──────────────────────────────────────
@Composable
private fun TableHeaderCell(
    text: String,
    width: Int,
    isFirst: Boolean = false
) {
    Box(
        modifier = Modifier
            .width(width.dp)
            .padding(vertical = 10.dp)
            .then(
                if (!isFirst) Modifier.border(
                    width = 0.5.dp,
                    color = BlueLight.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(0.dp)
                ) else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = if (isFirst) 12.sp else 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            letterSpacing = 0.5.sp,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}

// ─── Celda de datos ───────────────────────────────────────────
@Composable
private fun TableDataCell(
    text: String,
    width: Int,
    textColor: Color = Color(0xFF333333),
    isBold: Boolean = false,
    isFirst: Boolean = false
) {
    Box(
        modifier = Modifier
            .width(width.dp)
            .padding(vertical = 9.dp, horizontal = if (isFirst) 8.dp else 2.dp),
        contentAlignment = if (isFirst) Alignment.CenterStart else Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = if (isFirst) 12.sp else 12.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = textColor,
            textAlign = if (isFirst) TextAlign.Start else TextAlign.Center,
            maxLines = if (isFirst) 2 else 1,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 16.sp
        )
    }
}

// ─── Chip de resumen en el header ────────────────────────────
@Composable
private fun SummaryChip(value: String, label: String, chipColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = chipColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = value,
            fontWeight = FontWeight.Black,
            color = Color.White,
            fontSize = 20.sp
        )
        Text(
            text = label,
            fontSize = 9.sp,
            color = Color.White.copy(alpha = 0.80f),
            letterSpacing = 0.8.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
package com.example.appsicenet.ui.theme.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appsicenet.ViewModel.CargaAcademicaViewModel
import com.example.appsicenet.ViewModel.CargaAcademicaViewModelFactory
import com.example.appsicenet.datos.repository.LocalSNRepository
import com.example.appsicenet.ui.theme.Screens.StatCardModern
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

    // Paleta de colores moderna
    val primaryColor = Color(0xFF0066B3)    
    val secondaryColor = Color(0xFF00A86B)
    val accentColor = Color(0xFFFF6B6B)
    val backgroundColor = Color(0xFFF8F9FA)
    val surfaceColor = Color.White

    var selectedFilter by remember { mutableStateOf("Todos") }
    val filters = listOf("Todos", "Por semestre", "Créditos")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Header con diseño moderno
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(primaryColor, secondaryColor)
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    // Título con icono
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Mi Carga Académica",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 28.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Fecha de actualización con diseño mejorado
                    ultimaActualizacion?.let {
                        val fmt = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Update,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Actualizado: ${fmt.format(Date(it))}",
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Estadísticas en tarjetas modernas
                    if (carga.isNotEmpty()) {
                        val totalCreditos = carga.sumOf { it.creditos }
                        val promedioSemestre = carga.groupBy { it.semestre }
                            .map { (_, materias) -> materias.sumOf { it.creditos } }
                            .average()
                            .toInt()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatCardModern(
                                value = "${carga.size}",
                                label = "Materias",
                                icon = Icons.Default.Book,
                                color = Color(0xFF4ECDC4)
                            )
                            StatCardModern(
                                value = "$totalCreditos",
                                label = "Créditos",
                                icon = Icons.Default.Star,
                                color = Color(0xFFFF6B6B)
                            )
                            StatCardModern(
                                value = "$promedioSemestre",
                                label = "Promedio",
                                icon = Icons.Default.TrendingUp,
                                color = Color(0xFFFFB347)
                            )
                        }
                    }
                }
            }

            // Filtros horizontales
            if (carga.isNotEmpty()) {
                ScrollableFilterRow(
                    filters = filters,
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it },
                    primaryColor = primaryColor,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Lista de materias
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                if (carga.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    color = primaryColor,
                                    strokeWidth = 3.dp,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Cargando tu carga académica...",
                                    color = primaryColor,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                } else {
                    // Aplicar filtro aquí si es necesario
                    items(carga) { materia ->
                        CargaAcademicaItemModern(materia)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun StatCardModern(
    value: String,
    label: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = Modifier
            .size(90.dp, 100.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = color.copy(alpha = 0.1f),
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = label,
                fontSize = 10.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ScrollableFilterRow(
    filters: List<String>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    primaryColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            val isSelected = selectedFilter == filter
            FilterChip(
                selected = isSelected,
                onClick = { onFilterSelected(filter) },
                label = {
                    Text(
                        text = filter,
                        color = if (isSelected) Color.White else Color.Black,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = primaryColor,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White,
                    labelColor = Color.Black
                ),
                border = null,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
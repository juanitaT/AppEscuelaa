
package com.example.appsicenet.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appsicenet.datos.local.entity.CargaAcademicaEntity

@Composable
fun CargaAcademicaItemModern(materia: CargaAcademicaEntity) {

    // Colores modernos
    val cardColors = listOf(
        Color(0xFFE3F2FD), // Azul claro
        Color(0xFFE8F5E9), // Verde claro
        Color(0xFFFFF3E0), // Naranja claro
        Color(0xFFF3E5F5), // Púrpura claro
    )

    // Seleccionar color basado en el nombre de la materia para consistencia
    val backgroundColor = cardColors[Math.abs(materia.nombreMateria.hashCode() % cardColors.size)]

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor.copy(alpha = 0.3f))
        ) {
            // Barra superior con color acentuado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                            colors = listOf(Color(0xFF0066B3), Color(0xFF00A86B))
                        )
                    )
            )

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header con nombre y badge de créditos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = materia.nombreMateria,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF1A1A1A),
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Badge de créditos moderno
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF00A86B).copy(alpha = 0.1f),
                        shadowElevation = 0.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Grade,
                                contentDescription = null,
                                tint = Color(0xFF00A86B),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${materia.creditos} créditos",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF00A86B)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Clave y grupo en línea
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF0066B3).copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = " ${materia.claveMateria} ",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF0066B3),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFFF6B6B).copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = " Grupo ${materia.grupo} ",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFFF6B6B),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Línea divisoria sutil
                Divider(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Docente con diseño mejorado
                InfoRowModern(
                    icon = Icons.Default.Person,
                    label = "Docente",
                    value = materia.docente,
                    iconColor = Color(0xFF0066B3)
                )

                // Horario con diseño mejorado
                InfoRowModern(
                    icon = Icons.Default.Schedule,
                    label = "Horario",
                    value = materia.horario,
                    iconColor = Color(0xFF00A86B)
                )

                // Semestre (información adicional)
                if (materia.semestre > 0) {
                    InfoRowModern(
                        icon = Icons.Default.MenuBook,
                        label = "Semestre",
                        value = "${materia.semestre}°",
                        iconColor = Color(0xFFFF6B6B)
                    )
                }

                // Indicador de estado (puedes personalizarlo)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF00A86B).copy(alpha = 0.1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(
                                        color = Color(0xFF00A86B),
                                        shape = CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Activo",
                                fontSize = 10.sp,
                                color = Color(0xFF00A86B),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRowModern(
    icon: ImageVector,
    label: String,
    value: String,
    iconColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = iconColor.copy(alpha = 0.1f),
            modifier = Modifier.size(24.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$label:",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF555555)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = value,
            fontSize = 13.sp,
            color = Color(0xFF333333),
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
    }
}
package com.example.appsicenet.ui.theme.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.ConfirmationDialog

// ─── Colores AZULES ─────────────────────────────────────────────
private val bluePrimary = Color(0xFF1976D2)    // Azul principal
private val blueLight = Color(0xFF42A5F5)      // Azul claro
private val blueDark = Color(0xFF0D47A1)       // Azul oscuro

@Composable
fun DrawerContent(
    onNavigate: (String) -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header con gradiente AZUL
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            blueDark,      // Azul oscuro
                            bluePrimary,   // Azul principal
                            blueLight      // Azul claro
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "SICENET",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 28.sp,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "Sistema de Consulta",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Elementos del menú
        DrawerMenuItem(
            icon = Icons.Default.Person,
            texto = "Perfil",
            onClick = { onNavigate(Routes.PERFIL) }
        )

        DrawerMenuItem(
            icon = Icons.Default.School,
            texto = "Carga Académica",
            onClick = { onNavigate(Routes.CARGA) }
        )

        DrawerMenuItem(
            icon = Icons.Default.Assignment,
            texto = "Cardex",
            onClick = { onNavigate(Routes.CARDEX) }
        )

        DrawerMenuItem(
            icon = Icons.Default.Grade,
            texto = "Calificaciones",
            onClick = { onNavigate(Routes.CALIFICACIONES) }
        )

        Spacer(modifier = Modifier.weight(1f))

        Divider(
            color = blueLight.copy(alpha = 0.3f),  // Cambiado a azul claro
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Opción de cerrar sesión
        DrawerMenuItem(
            icon = Icons.Default.ExitToApp,
            texto = "Cerrar Sesión",
            onClick = { showLogoutDialog = true },
            isLogout = true
        )

        Spacer(modifier = Modifier.height(16.dp))
    }

    // Diálogo de confirmación para cerrar sesión
    if (showLogoutDialog) {
        ConfirmationDialog(
            title = "Cerrar Sesión",
            message = "¿Está seguro que desea cerrar sesión?",
            onConfirm = {
                onNavigate(Routes.LOGIN)
            },
            onDismiss = {
                showLogoutDialog = false
            },
            confirmText = "Cerrar Sesión",
            dismissText = "Cancelar"
        )
    }
}

@Composable
fun DrawerMenuItem(
    icon: ImageVector,
    texto: String,
    onClick: () -> Unit,
    isLogout: Boolean = false
) {
    val textColor = if (isLogout) Color(0xFFD32F2F) else Color.Black
    val iconColor = if (isLogout) Color(0xFFD32F2F) else bluePrimary  // Cambiado a azul principal

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = texto,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}
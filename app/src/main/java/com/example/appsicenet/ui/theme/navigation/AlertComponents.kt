package com.example.appsicenet.ui.theme.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Colores AZULES ─────────────────────────────────────────────
val BluePrimary = Color(0xFF1976D2)    // Azul principal
val BlueLight = Color(0xFF42A5F5)      // Azul claro
val BlueDark = Color(0xFF0D47A1)       // Azul oscuro
val RedError = Color(0xFFD32F2F)        // Rojo (mantenido para errores)
val BlueInfo = Color(0xFF1976D2)        // Azul para info

enum class AlertType {
    SUCCESS,
    ERROR,
    INFO
}

/**
 * Componente de Snackbar personalizado para mostrar mensajes al usuario
 *
 * @param message Mensaje a mostrar
 * @param type Tipo de alerta (SUCCESS, ERROR, INFO)
 * @param modifier Modificador opcional
 */
@Composable
fun CustomSnackbar(
    message: String,
    type: AlertType = AlertType.INFO,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, icon, iconTint) = when (type) {
        AlertType.SUCCESS -> Triple(
            BluePrimary,          // Azul principal para éxito
            Icons.Default.CheckCircle,
            Color.White
        )
        AlertType.ERROR -> Triple(
            RedError,             // Rojo para error (se mantiene)
            Icons.Default.Error,
            Color.White
        )
        AlertType.INFO -> Triple(
            BlueLight,            // Azul claro para info
            Icons.Default.Info,
            Color.White
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = message,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * Diálogo de confirmación para acciones importantes
 *
 * @param title Título del diálogo
 * @param message Mensaje del diálogo
 * @param onConfirm Acción al confirmar
 * @param onDismiss Acción al cancelar
 * @param confirmText Texto del botón de confirmación (por defecto "Confirmar")
 * @param dismissText Texto del botón de cancelar (por defecto "Cancelar")
 */
@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = "Confirmar",
    dismissText: String = "Cancelar"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = BluePrimary  // Cambiado a azul principal
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black.copy(alpha = 0.8f)
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(
                    text = confirmText,
                    color = BluePrimary,  // Cambiado a azul principal
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = dismissText,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}
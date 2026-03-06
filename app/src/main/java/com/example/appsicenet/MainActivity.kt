package com.example.appsicenet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appsicenet.ui.theme.AppSicenetTheme
import com.example.appsicenet.ui.theme.Screens.CalificacionesScreen
import com.example.appsicenet.ui.theme.Screens.CardexScreen
import com.example.appsicenet.ui.theme.Screens.CargaAcademicaScreen
import com.example.appsicenet.ui.theme.Screens.HomeScreen
import com.example.appsicenet.ui.theme.Screens.PerfilScreen
import com.example.appsicenet.ui.theme.navigation.AppScaffold
import com.example.appsicenet.ui.theme.navigation.Routes

/**
 * La actividad principal que organiza toda la navegación y las pantallas de la app.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Obtenemos los repositorios (datos) desde la clase AppSicenet
        val appContainer = (application as AppSicenet).container

        setContent {
            AppSicenetTheme {
                // Creamos el "GPS"(navControler) para movernos entre pantallas
                val navController = rememberNavController()

                // Definimos el mapa de navegación (qué pantalla mostrar según la ruta)
                NavHost(
                    navController = navController,
                    startDestination = Routes.LOGIN // La app empieza en el Login
                ) {

                    // Pantalla de Inicio / Login
                    composable(Routes.LOGIN) {
                        HomeScreen(
                            onLoginSuccess = {
                                // Si el login es correcto, vamos al Perfil y borramos el Login del historial
                                navController.navigate(Routes.PERFIL) {
                                    popUpTo(Routes.LOGIN) { inclusive = true }
                                }
                            },
                            snRepository = appContainer.networkSNRepository,
                            localRepository = appContainer.localSNRepository
                        )
                    }

                    // Pantalla del Perfil del Alumno
                    composable(Routes.PERFIL) {
                        AppScaffold(navController) {
                            PerfilScreen(
                                matricula = SessionManager.matricula,
                                localRepository = appContainer.localSNRepository
                            )
                        }
                    }

                    // Pantalla de Carga Académica (materias actuales)
                    composable(Routes.CARGA) {
                        AppScaffold(navController) {
                            CargaAcademicaScreen(
                                localRepository = appContainer.localSNRepository
                            )
                        }
                    }

                    // Pantalla de Kárdex (historial de calificaciones)
                    composable(Routes.CARDEX) {
                        AppScaffold(navController) {
                            CardexScreen(
                                localRepository = appContainer.localSNRepository
                            )
                        }
                    }

                    // Pantalla de Calificaciones actuales
                    composable(Routes.CALIFICACIONES){
                        AppScaffold(navController) {
                            CalificacionesScreen(
                                localRepository = appContainer.localSNRepository
                            )
                        }
                    }
                }
            }
        }
    }
}
package com.example.appsicenet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material3.AppScaffold
import com.example.appsicenet.ui.theme.AppSicenetTheme
import com.example.appsicenet.ui.theme.Screens.CalificacionesScreen
import com.example.appsicenet.ui.theme.Screens.CardexScreen
import com.example.appsicenet.ui.theme.Screens.CargaAcademicaScreen
import com.example.appsicenet.ui.theme.Screens.HomeScreen
import com.example.appsicenet.ui.theme.Screens.PerfilScreen
import com.example.appsicenet.ui.theme.navigation.Routes

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as SiccenetApp).container

        setContent {
            AppSicenetTheme {

                val navController: Modifier = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.LOGIN
                ) {


                    composable(Routes.LOGIN) {
                        HomeScreen(
                            onLoginSuccess = {
                                navController.navigate(Routes.PERFIL) {
                                    popUpTo(Routes.LOGIN) { inclusive = true }
                                }
                            },
                            snRepository = appContainer.networkSNRepository,
                            localRepository = appContainer.localSNRepository
                        )
                    }

                    composable(Routes.PERFIL) {

                        //val matriculaUsuario =   // o donde la estés guardando

                        AppScaffold(navController) {
                            PerfilScreen(
                                matricula = SessionManager.matricula,
                                localRepository = appContainer.localSNRepository
                            )
                        }
                    }

                    composable(Routes.CARGA) {
                        AppScaffold(navController) {
                            CargaAcademicaScreen(
                                localRepository = appContainer.localSNRepository
                            )
                        }
                    }

                    composable(Routes.CARDEX) {
                        AppScaffold(navController) {
                            CardexScreen(
                                localRepository = appContainer.localSNRepository
                            )
                        }
                    }

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
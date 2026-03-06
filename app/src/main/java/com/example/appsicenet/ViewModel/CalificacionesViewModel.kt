package com.example.appsicenet.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OverwritingInputMerger
import androidx.work.WorkManager
import androidx.work.setInputMerger
import com.example.appsicenet.SessionManager
import com.example.appsicenet.datos.local.entity.CalificacionFinalEntity
import com.example.appsicenet.datos.local.entity.CalificacionUnidadEntity
import com.example.appsicenet.datos.repository.LocalSNRepository
import com.example.appsicenet.datos.worker.SicenetCalificacionesDbWorker
import com.example.appsicenet.datos.worker.SicenetCalificacionesWorker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CalificacionesViewModel(
    private val repository: LocalSNRepository,
    private val workManager: WorkManager
) : ViewModel() {

    // Materias inscritas
    private val matricula = SessionManager.matricula
    private val cargaAcademica = repository.obtenerCargaAcademica(matricula)

    // Calificaciones guardadas
    private val calificacionesFinalesRaw = repository.obtenerCalificacionesFinales()
    private val calificacionesUnidadRaw = repository.obtenerCalificacionesUnidad()

    val calificacionesFinales: StateFlow<List<CalificacionFinalEntity>> =
        combine(cargaAcademica, calificacionesFinalesRaw) { carga, finales ->

            Log.d("DEBUG_CALIF", "Materias carga: ${carga.size}")
            Log.d("DEBUG_CALIF", "Finales DB: ${finales.size}")

            carga.map { materia ->

                finales.find {
                    it.materia.equals(materia.nombreMateria, ignoreCase = true)
                }
                    ?: CalificacionFinalEntity(
                        materia = materia.nombreMateria,
                        calificacionFinal = 0,
                        acreditado = "NP",
                        ultimaActualizacion = System.currentTimeMillis()
                    )
            }

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val calificacionesUnidad: StateFlow<List<CalificacionUnidadEntity>> =
        combine(cargaAcademica, calificacionesUnidadRaw) { carga, unidades ->

            val listaCompleta = mutableListOf<CalificacionUnidadEntity>()

            carga.forEach { materia ->

                val unidadesMateria = unidades.filter {
                    it.materia.equals(materia.nombreMateria, ignoreCase = true)
                }

                if (unidadesMateria.isNotEmpty()) {

                    listaCompleta.addAll(unidadesMateria)

                } else {

                    // Si no hay unidades capturadas aún
                    listaCompleta.add(
                        CalificacionUnidadEntity(
                            materia = materia.nombreMateria,
                            unidad = 1,
                            calificacion = 0,
                            ultimaActualizacion = System.currentTimeMillis()
                        )
                    )
                }
            }

            listaCompleta
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun verificarYSincronizar() {

        viewModelScope.launch {

            val finales = calificacionesFinalesRaw.first()

            val necesitaSincronizar =
                finales.isEmpty()

            if (necesitaSincronizar) {

                Log.d("DEBUG_CALIF", "Sincronizando calificaciones...")

                sincronizar()

            } else {

                Log.d("DEBUG_CALIF", "Calificaciones ya guardadas")

            }
        }
    }

    private fun sincronizar() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workerRed =
            OneTimeWorkRequestBuilder<SicenetCalificacionesWorker>()
                .setConstraints(constraints)
                .build()

        val workerDb =
            OneTimeWorkRequestBuilder<SicenetCalificacionesDbWorker>()
                .setInputMerger(OverwritingInputMerger::class)
                .build()

        workManager
            .beginUniqueWork(
                "calificaciones_sync",
                ExistingWorkPolicy.KEEP,
                workerRed
            )
            .then(workerDb)
            .enqueue()
    }
}
class CalificacionesViewModelFactory(
    private val repository: LocalSNRepository,
    private val workManager: WorkManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CalificacionesViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return CalificacionesViewModel(repository, workManager) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
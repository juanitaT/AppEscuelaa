package com.example.appsicenet.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.appsicenet.SessionManager
import com.example.appsicenet.datos.local.entity.CargaAcademicaEntity
import com.example.appsicenet.datos.repository.LocalSNRepository
import com.example.appsicenet.datos.sync.CargaAcademicaSyncManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class CargaAcademicaViewModel(
    private val localRepository: LocalSNRepository,
    private val workManager: WorkManager
) : ViewModel() {

    private val matricula = SessionManager.matricula

    val carga: StateFlow<List<CargaAcademicaEntity>> =
        localRepository.obtenerCargaAcademica(matricula)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val ultimaActualizacion: StateFlow<Long?> =
        localRepository.obtenerUltimaActualizacionCargaFlow(matricula)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    //    Sincroniza solo si:
//    No hay materias guardadas

    fun verificarYSincronizar() {
        viewModelScope.launch {

            val datos = carga.first()
            val ultima = ultimaActualizacion.first()

            val ahora = System.currentTimeMillis()

            val necesitaSincronizar =
                datos.isEmpty() ||
                        ultima == null ||
                        (ahora - ultima) > 1000 * 60 * 60 // 1 hora para recargar los datos y sincronizarlos de nuevo

            if (necesitaSincronizar) {
                sincronizar()
            }
        }
    }

    private fun sincronizar() {
        CargaAcademicaSyncManager.sincronizar(workManager)
    }
}
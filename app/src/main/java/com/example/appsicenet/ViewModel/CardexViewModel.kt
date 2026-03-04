package com.example.appsicenet.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.appsicenet.datos.local.entity.CardexEntity
import com.example.appsicenet.datos.repository.LocalSNRepository
import com.example.appsicenet.datos.sync.CardexSyncManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class CardexViewModel(
    private val localRepository: LocalSNRepository,
    private val workManager: WorkManager
) : ViewModel() {

    val cardex: StateFlow<List<CardexEntity>> =
        localRepository.obtenerCardex()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val ultimaActualizacion: StateFlow<Long?> =
        localRepository.obtenerUltimaActualizacionCardex()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )

    fun verificarYSincronizar() {
        viewModelScope.launch {

            val datos = cardex.first()
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
        CardexSyncManager.sincronizar(workManager)
    }
}
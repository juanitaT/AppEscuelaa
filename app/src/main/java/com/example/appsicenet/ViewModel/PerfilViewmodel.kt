package com.example.appsicenet.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.appsicenet.datos.local.entity.PerfilEntity
import com.example.appsicenet.datos.repository.LocalSNRepository
import com.example.appsicenet.datos.worker.SicenetPerfilDbWorker
import com.example.appsicenet.datos.worker.SicenetPerfilWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val localRepository: LocalSNRepository,
    private val workManager: WorkManager
) : ViewModel() {

    fun obtenerPerfil(matricula: String): Flow<PerfilEntity?> {
        return localRepository.obtenerPerfil(matricula)
    }

    fun verificarYSincronizarPerfil(matricula: String) {

        viewModelScope.launch {

            val perfilLocal = localRepository
                .obtenerPerfil(matricula)
                .first()

            //Si no hay datos, sincronizar
            if (perfilLocal == null) {
                sincronizarPerfil()
            }
        }
    }

    private fun sincronizarPerfil() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workerPerfilRed = OneTimeWorkRequestBuilder<SicenetPerfilWorker>()
            .setConstraints(constraints)
            .build()

        val workerPerfilDb = OneTimeWorkRequestBuilder<SicenetPerfilDbWorker>()
            .build()

        workManager
            .beginUniqueWork(
                "perfil_sync",
                ExistingWorkPolicy.KEEP,
                workerPerfilRed
            )
            .then(workerPerfilDb)
            .enqueue()
    }
}
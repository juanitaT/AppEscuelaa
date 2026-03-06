package com.example.appsicenet.datos.sync

import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OverwritingInputMerger
import androidx.work.WorkManager
import androidx.work.setInputMerger
import com.example.appsicenet.datos.worker.SicenetCalificacionesDbWorker
import com.example.appsicenet.datos.worker.SicenetCalificacionesWorker

object CalificacionesSyncManager {

    fun sincronizar(workManager: WorkManager) {

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
package com.example.appsicenet.datos.sync

import SicenetCargaAcademicaWorker
import androidx.work.*
import com.example.appsicenet.datos.worker.SicenetCargaAcademicaDbWorker


object CargaAcademicaSyncManager {

    fun sincronizar(workManager: WorkManager) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val red = OneTimeWorkRequestBuilder<SicenetCargaAcademicaWorker>()
            .setConstraints(constraints)
            .addTag("WM_CARGA_RED")
            .build()

        val db = OneTimeWorkRequestBuilder<SicenetCargaAcademicaDbWorker>()
            .addTag("WM_CARGA_DB")
            .build()

        workManager
            .beginUniqueWork(
                "WM_CARGA_ACADEMICA",
                ExistingWorkPolicy.REPLACE,
                red
            )
            .then(db)
            .enqueue()
    }
}
package com.example.appsicenet.datos.sync

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.appsicenet.datos.worker.SicenetCardexDbWorker
import com.example.appsicenet.datos.worker.SicenetCardexWorker


object CardexSyncManager {

    fun sincronizar(workManager: WorkManager) {

        val red = OneTimeWorkRequestBuilder<SicenetCardexWorker>()
            .addTag("WM_CARDEX_RED")
            .build()

        val db = OneTimeWorkRequestBuilder<SicenetCardexDbWorker>()
            .addTag("WM_CARDEX_DB")
            .build()

        workManager
            .beginUniqueWork(
                "WM_CARDEX",
                ExistingWorkPolicy.REPLACE,
                red
            )
            .then(db)
            .enqueue()
    }
}
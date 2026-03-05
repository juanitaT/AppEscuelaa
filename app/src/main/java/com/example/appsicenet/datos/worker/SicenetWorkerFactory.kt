package com.example.appsicenet.datos.worker

import SicenetCargaAcademicaWorker
import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.appsicenet.datos.repository.LocalSNRepository
import com.example.appsicenet.datos.repository.SNRepository
import kotlin.jvm.java


class SicenetWorkerFactory(
    private val networkRepository: SNRepository,
    private val localRepository: LocalSNRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {

            SicenetPerfilWorker::class.java.name ->
                SicenetPerfilWorker(
                    appContext,
                    workerParameters,
                    networkRepository
                )

            SicenetPerfilDbWorker::class.java.name ->
                SicenetPerfilDbWorker(
                    appContext,
                    workerParameters,
                    localRepository
                )

            SicenetCalificacionesWorker::class.java.name ->
                SicenetCalificacionesWorker(
                    appContext,
                    workerParameters,
                    networkRepository
                )

            SicenetCalificacionesDbWorker::class.java.name ->
                SicenetCalificacionesDbWorker(
                    appContext,
                    workerParameters,
                    localRepository
                )

            SicenetCargaAcademicaWorker::class.java.name ->
                SicenetCargaAcademicaWorker(
                    appContext,
                    workerParameters,
                    networkRepository
                )

            SicenetCargaAcademicaDbWorker::class.java.name ->
                SicenetCargaAcademicaDbWorker(
                    appContext,
                    workerParameters,
                    localRepository
                )

            else -> null
        }
    }
}
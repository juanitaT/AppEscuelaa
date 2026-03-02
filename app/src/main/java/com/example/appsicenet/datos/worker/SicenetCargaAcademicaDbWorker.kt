package com.example.appsicenet.datos.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.appsicenet.datos.mapper.CargaAcademicaXmlParser
import com.example.appsicenet.datos.repository.LocalSNRepository

import kotlinx.coroutines.flow.first

class SicenetCargaAcademicaDbWorker(
    context: Context,
    params: WorkerParameters,
    private val localRepository: LocalSNRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            val xml = inputData.getString("carga_xml")
                ?: return Result.failure()

            val matricula = inputData.getString("matricula")
                ?: return Result.failure()

            Log.d("WM_CARGA_DB", "Procesando XML de carga académica para $matricula")

            val perfil = localRepository
                .obtenerPerfil(matricula)
                .first()
                ?: return Result.failure()

            val lista = CargaAcademicaXmlParser.parse(
                xml = xml,
                matricula = perfil.matricula,
                semestre = perfil.semActual
            )

            localRepository.guardarCargaAcademica(
                matricula,
                lista
            )

            Log.d("WM_CARGA_DB", "Carga académica actualizada (${lista.size})")

            Result.success()

        } catch (e: Exception) {
            Log.e("WM_CARGA_DB", "Error guardando carga académica", e)
            Result.failure()
        }
    }
}
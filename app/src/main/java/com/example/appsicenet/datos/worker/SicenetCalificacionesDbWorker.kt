package com.example.appsicenet.datos.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.appsicenet.datos.mapper.CalificacionesXmlParser
import com.example.appsicenet.datos.repository.LocalSNRepository


class SicenetCalificacionesDbWorker(
    context: Context,
    params: WorkerParameters,
    private val localRepository: LocalSNRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val unidadesXml = inputData.getString("unidades_xml")
                ?: return Result.failure()
            val finalesXml = inputData.getString("finales_xml")
                ?: return Result.failure()

            Log.d("WM_CALIF_DB", "Procesando XML de calificaciones")

            val resultado = CalificacionesXmlParser.parse(unidadesXml, finalesXml)


            // guardar calif final
            localRepository.guardarCalificacionesFinales(resultado.finales)

            // guardar calif unidad
            localRepository.guardarCalificacionesUnidad(resultado.unidades)

            Log.d("WM_CALIF_DB", "Calificaciones guardadas en Room")
            Result.success()
        } catch (e: Exception) {
            Log.e("WM_CALIF_DB", "Error guardando calificaciones", e)
            Result.failure()
        }
    }
}
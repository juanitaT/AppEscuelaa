package com.example.appsicenet.datos.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.appsicenet.datos.repository.SNRepository

class SicenetCalificacionesWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: SNRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            Log.d("WM_CALIF_RED", "Consultando calificaciones...")

            val unidadesXml = try {
                repository.obtenerCalificacionesUnidadesXml()
            } catch (e: Exception) {
                Log.e("WM_CALIF_RED", "Error obteniendo unidades", e)
                ""
            }

            var finalesXml = try {
                repository.obtenerCalificacionesFinalesXml(1)
            } catch (e: Exception) {
                Log.e("WM_CALIF_RED", "Error obteniendo finales (mod 1)", e)
                ""
            }

            if (!finalesXml.contains("lstCalif") && !finalesXml.contains("[{")) {

                try {
                    val finalesFallback = repository.obtenerCalificacionesFinalesXml(0)
                    if (finalesFallback.contains("lstCalif") || finalesFallback.contains("[{")) {
                        finalesXml = finalesFallback

                    }
                } catch (e: Exception) {

                }
            }


            if (unidadesXml.isBlank() && finalesXml.isBlank()) {
                return Result.failure()
            }

            Result.success(
                workDataOf(
                    "unidades_xml" to unidadesXml,
                    "finales_xml" to finalesXml
                )
            )

        } catch (e: Exception) {
            Log.e("WM_CALIF_RED", "Error", e)
            Result.failure()
        }
    }
}
package com.example.appsicenet.datos.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.Data
import com.example.appsicenet.datos.repository.SNRepository

class SicenetPerfilWorker(
    context: Context,
    params: WorkerParameters,
    private val networkRepository: SNRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("WM_PERFIL", "Worker 1 iniciado")

            val perfilJson = networkRepository.obtenerPerfilJson()

            Log.d("WM_PERFIL", "Perfil recibido: $perfilJson")

            val output = Data.Builder()
                .putString("perfil_json", perfilJson)
                .build()

            Result.success(output)

        } catch (e: Exception) {
            Log.e("WM_PERFIL", "Error en Worker 1", e)
            Result.failure()
        }
    }
}
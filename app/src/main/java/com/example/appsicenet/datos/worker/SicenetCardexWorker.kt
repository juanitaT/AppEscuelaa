package com.example.appsicenet.datos.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.appsicenet.di.DefaultAppContainer


class SicenetCardexWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            Log.d("WM_CARDEX_RED", "Consultando cardex")

            val container = DefaultAppContainer(applicationContext)
            val repository = container.NetworSNRepository

            val xml = repository.obtenerCardexXml()

            Log.d("CARDEX_XML_COMPLETO", xml)
            Log.d("WM_CARDEX_RED", "XML recibido correctamente")

            Result.success(
                workDataOf("cardex_xml" to xml)
            )

        } catch (e: Exception) {
            Log.e("WM_CARDEX_RED", "Error", e)
            Result.failure()
        }
    }
}
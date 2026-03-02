package com.example.appsicenet.datos.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.appsicenet.datos.db.SicenetDatabase
import com.example.appsicenet.datos.mapper.CardexXmlParser

class SicenetCardexDbWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val xml = inputData.getString("cardex_xml")
            ?: return Result.failure()

        val db = SicenetDatabase.getDatabase(applicationContext)

        val lista = CardexXmlParser.parse(xml)
        db.cardexDao().limpiar()
        db.cardexDao().insertarTodo(lista)

        return Result.success()
    }
}
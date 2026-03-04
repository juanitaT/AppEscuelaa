package com.example.appsicenet.datos.worker

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import android.content.Context
import com.example.appsicenet.datos.local.entity.PerfilEntity
import com.example.appsicenet.datos.modelo.PerfilAlumnos
import com.example.appsicenet.datos.repository.LocalSNRepository

class SicenetPerfilDbWorker(
    context: Context,
    params: WorkerParameters,
    private val localRepository: LocalSNRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            val perfilJson = inputData.getString("perfil_json")

            if (perfilJson.isNullOrEmpty()) {

                return Result.failure()
            }



            val perfil = Gson().fromJson(perfilJson, PerfilAlumnos::class.java)

            val entity = PerfilEntity(
                matricula = perfil.matricula,
                nombre = perfil.nombre,
                carrera = perfil.carrera,
                especialidad = perfil.especialidad,
                estatus = perfil.estatus,
                semActual = perfil.semActual,
                cdtosAcumulados = perfil.cdtosAcumulados,
                cdtosActuales = perfil.cdtosActuales,
                fechaReins = perfil.fechaReins,
                modEducativo = perfil.modEducativo,
                urlFoto = perfil.urlFoto,
                inscrito = perfil.inscrito,
                ultimaActualizacion = System.currentTimeMillis()
            )


            localRepository.guardarPerfil(entity)

            Result.success()

        } catch (e: Exception) {
            Result.failure()
        }
    }
}
package com.example.appsicenet.datos.repository

import android.util.Log
import com.example.appsicenet.datos.modelo.LoginResult
import com.example.appsicenet.datos.modelo.PerfilAlumnos
import com.example.appsicenet.datos.remote.SICENETWService
import com.example.appsicenet.datos.remote.SoapRequestBuilder
import com.google.gson.Gson

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class NetworSNRepository(
    private val snApiService: SICENETWService
) : SNRepository {

    override suspend fun acceso(
        m: String,
        p: String
    ): LoginResult {

        val body = SoapRequestBuilder.login(m, p)
            .toRequestBody("text/xml; charset=utf-8".toMediaType())

        val response = snApiService.acceso(body)

        val xml = response.string()
        Log.d("SICENET_XML", xml)

        val accesoCorrecto =
            xml.contains("\"acceso\":true")

        Log.d("SICENET_LOGIN", "Acceso correcto = $accesoCorrecto")

        return if (accesoCorrecto) {
            LoginResult(
                success = true,
                message = "Login correcto"
            )
        } else {
            LoginResult(
                success = false,
                message = "Credenciales inválidas"
            )
        }
    }
    override suspend fun obtenerPerfil(): PerfilAlumnos {

        val body = SoapRequestBuilder.perfil()
            .toRequestBody("text/xml; charset=utf-8".toMediaType())

        val response = snApiService.getAlumnoAcademico(body)
        val xml = response.string()

        Log.d("SICENET_PERFIL_XML", xml)

        val json = extraerJson(xml)

        Log.d("SICENET_PERFIL_JSON", json)

        return Gson().fromJson(json, PerfilAlumnos::class.java)
    }

    private fun extraerJson(xml: String): String {
        return xml.substringAfter("<getAlumnoAcademicoResult>")
            .substringBefore("</getAlumnoAcademicoResult>")
    }

}
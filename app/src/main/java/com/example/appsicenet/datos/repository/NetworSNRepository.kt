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
    // consulta para el login
    override suspend fun acceso(
        m: String,
        p: String
    ): LoginResult {

        val body = SoapRequestBuilder.login(m, p)
            .toRequestBody("text/xml; charset=utf-8".toMediaType())

        val response = snApiService.acceso(body)

        // Extrae la respuesta del servidor
        val xml = response.string()
        // Registra la respuesta
        Log.d("SICENET_XML", xml)

        // Analiza la respuesta para determinar si el login fue exitoso.
        // Busca una subcadena específica que indica un acceso correcto.
        val accesoCorrecto =
            xml.contains("\"acceso\":true")

        // Registra el resultado del intento de login.
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

    // Obtiene el perfil académico del alumno que ha iniciado sesión.
    // corrutina
    override suspend fun obtenerPerfil(): PerfilAlumnos {

        val body = SoapRequestBuilder.perfil()
            .toRequestBody("text/xml; charset=utf-8".toMediaType())

        val response = snApiService.getAlumnoAcademico(body)

        val xml = response.string()

        // Registra
        Log.d("SICENET_PERFIL_XML", xml)

        val json = extraerJson(xml)

        // para ver la informacion del perfil desde el logcat
        Log.d("SICENET_PERFIL_JSON", json)

        return Gson().fromJson(json, PerfilAlumnos::class.java)
    }

    private fun extraerJson(xml: String): String {
        return xml.substringAfter("<getAlumnoAcademicoResult>")
            .substringBefore("</getAlumnoAcademicoResult>")
    }

}

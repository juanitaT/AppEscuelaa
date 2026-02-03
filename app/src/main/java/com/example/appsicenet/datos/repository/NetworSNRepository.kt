package com.example.appsicenet.datos.repository

import android.util.Log
import com.example.appsicenet.datos.modelo.LoginResult
import com.example.appsicenet.datos.modelo.PerfilAlumnos
import com.example.appsicenet.datos.remote.SICENETWService
import com.example.appsicenet.datos.remote.SoapRequestBuilder

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
            xml.contains("<accesoLoginResult>true</accesoLoginResult>")

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
        TODO("Not yet implemented")
    }
}
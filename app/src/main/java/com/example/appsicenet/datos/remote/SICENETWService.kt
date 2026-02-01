package com.example.appsicenet.datos.remote

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SICENETWService {

    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/accesoLogin"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun acceso(
        @Body soap: RequestBody
    ): ResponseBody

    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/getAlumnoAcademico"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getAlumnoAcademico(
        @Body soap: RequestBody
    ): ResponseBody
}
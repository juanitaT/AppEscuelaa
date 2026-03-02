package com.example.appsicenet.datos.remote

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
// retrofit2 libreria que permite acceder al sicenet y los datos del usuario (retrofit2)
// aqui nos permite consultar el acceso y los datos academicos del alumno
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

    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/getAllKardexConPromedioByAlumno"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getCardex(
        @Body soap: RequestBody
    ): ResponseBody
    //cris
    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/getCalifUnidadesByAlumno"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getCalifUnidadesByAlumno(
        @Body soap: RequestBody
    ): ResponseBody

    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/getAllCalifFinalByAlumnos"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getAllCalifFinalByAlumnos(
        @Body soap: RequestBody
    ): ResponseBody


    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: http://tempuri.org/getCargaAcademicaByAlumno"
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getCargaAcademicaByAlumno(
        @Body soap: RequestBody
    ): ResponseBody
}

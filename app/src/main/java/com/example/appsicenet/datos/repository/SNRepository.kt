package com.example.appsicenet.datos.repository

import com.example.appsicenet.datos.modelo.LoginResult
import com.example.appsicenet.datos.modelo.PerfilAlumnos
//
interface SNRepository {

    suspend fun acceso(matricula: String, password: String): LoginResult
    suspend fun obtenerPerfil(): PerfilAlumnos
    suspend fun obtenerPerfilJson(): String

    suspend fun obtenerCardexXml(): String
    suspend fun obtenerCalificacionesUnidadesXml(): String
    suspend fun obtenerCalificacionesFinalesXml(modEducativo: Int): String


    //obtiene la carga academica
    suspend fun obtenerCargaAcademicaXml(): String
}
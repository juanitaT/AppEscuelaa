package com.example.appsicenet.datos.repository

import com.example.appsicenet.datos.modelo.LoginResult
import com.example.appsicenet.datos.modelo.PerfilAlumnos

interface SNRepository {
    suspend fun acceso(usuario: String, password: String): LoginResult
    suspend fun obtenerPerfil(): PerfilAlumnos
}
package com.example.appsicenet

object SessionManager {
    private var _matricula: String? = null

    val matricula: String
        get() = _matricula
            ?: throw IllegalStateException("Usuario no logueado")

    fun iniciarSesion(matricula: String) {
        _matricula = matricula
    }

    fun cerrarSesion() {
        _matricula = null
    }

    fun estaLogueado(): Boolean {
        return _matricula != null
    }
}
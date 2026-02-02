package com.example.appsicenet.datos.modelo

data class LoginResult(
    val success: Boolean,
    val cookie: String? = null,
    val message: String? = null
)
package com.example.appsicenet.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsicenet.datos.modelo.PerfilAlumnos
import com.example.appsicenet.datos.repository.SNRepository
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val snRepository: SNRepository
) : ViewModel() {

    var perfil by mutableStateOf<PerfilAlumnos?>(null)
        private set

    fun cargarPerfil() {
        viewModelScope.launch {
            perfil = snRepository.obtenerPerfil()
        }
    }
}
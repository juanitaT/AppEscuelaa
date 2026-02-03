package com.example.appsicenet.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsicenet.datos.repository.SNRepository


class PerfilViewModelFactory(
    private val snRepository: SNRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PerfilViewModel(snRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
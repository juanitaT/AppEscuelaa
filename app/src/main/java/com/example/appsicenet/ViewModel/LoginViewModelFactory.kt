package com.example.appsicenet.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsicenet.datos.repository.SNRepository

class LoginViewModelFactory(
    private val snRepository: SNRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(snRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
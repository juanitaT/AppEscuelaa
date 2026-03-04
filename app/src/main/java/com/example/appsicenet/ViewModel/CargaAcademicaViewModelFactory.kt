package com.example.appsicenet.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.example.appsicenet.datos.repository.LocalSNRepository

class CargaAcademicaViewModelFactory(
    private val localRepository: LocalSNRepository,
    private val context: Context
) : ViewModelProvider.Factory {

    private val workManager = WorkManager.getInstance(context)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CargaAcademicaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CargaAcademicaViewModel(
                localRepository,
                workManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
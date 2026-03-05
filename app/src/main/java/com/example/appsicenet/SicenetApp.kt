package com.example.appsicenet

import android.app.Application
import androidx.work.Configuration
import com.example.appsicenet.datos.worker.SicenetWorkerFactory
import com.example.appsicenet.di.DefaultAppContainer


class SicenetApp : Application(), Configuration.Provider {

    lateinit var container: DefaultAppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(
                SicenetWorkerFactory(
                    container.networkSNRepository,
                    container.localSNRepository
                )
            )
            .build()
}
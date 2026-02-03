package com.example.appsicenet.di

import android.content.Context
import com.example.appsicenet.datos.remote.AddCookiesInterceptor
import com.example.appsicenet.datos.remote.ReceivedCookiesInterceptor
import com.example.appsicenet.datos.remote.SICENETWService
import com.example.appsicenet.datos.repository.NetworSNRepository
import com.example.appsicenet.datos.repository.SNRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

interface AppContainer {

    val snRepository: SNRepository
}

class DefaultAppContainer(
    context: Context
) : AppContainer {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AddCookiesInterceptor(context))
        .addInterceptor(ReceivedCookiesInterceptor(context))
        .build()

    private val retrofitSN: Retrofit = Retrofit.Builder()
        .baseUrl("https://sicenet.surguanajuato.tecnm.mx/ws/")
        .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
        .client(okHttpClient)
        .build()

    private val sicenetService: SICENETWService by lazy {
        retrofitSN.create(SICENETWService::class.java)
    }

    override val snRepository: SNRepository by lazy {
        NetworSNRepository(sicenetService)
    }
}


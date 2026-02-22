package com.example.appsicenet.datos.remote

import android.content.Context
import android.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

//parte del material que nos dio el profesor en un repositorio para hacer funcionar la app

class AddCookiesInterceptor(
    private val context: Context
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // peticion hacia el servidor
        val builder: Request.Builder = chain.request().newBuilder()
    // obtiene
        val preferences =
            PreferenceManager.getDefaultSharedPreferences(context)
                .getStringSet(PREF_COOKIES, HashSet()) ?: HashSet()
        // envia las cookies hacia el servidor
        for (cookie in preferences) {
            builder.addHeader("Cookie", cookie)
        }

        return chain.proceed(builder.build())
    }

    companion object {
        const val PREF_COOKIES = "PREF_COOKIES"
    }
}
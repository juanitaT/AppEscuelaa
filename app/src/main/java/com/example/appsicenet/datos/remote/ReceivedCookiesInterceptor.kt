package com.example.appsicenet.datos.remote

import android.content.Context
import android.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

//parte del material que nos dio el profesor en un repositorio para hacer funcionar la app

class ReceivedCookiesInterceptor(
    private val context: Context
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // reciben
        val originalResponse: Response = chain.proceed(chain.request())
        // lee
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {

            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            //guardan de forma interna
            val cookies =
                prefs.getStringSet(AddCookiesInterceptor.PREF_COOKIES, HashSet())
                    ?: HashSet()

            //agrega
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            // mantienen
            prefs.edit()
                .putStringSet(AddCookiesInterceptor.PREF_COOKIES, cookies)
                .apply()
        }

        return originalResponse
    }
}
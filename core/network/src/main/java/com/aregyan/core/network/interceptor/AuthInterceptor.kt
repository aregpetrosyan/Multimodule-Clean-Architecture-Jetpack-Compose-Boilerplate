package com.aregyan.core.network.interceptor

import android.util.Base64
import okhttp3.Interceptor
import okhttp3.Response

/**
 * NOTE: The API key below is hardcoded and only encoded with Base64 (not real encryption).
 * This is insecure and should ONLY be used for local testing or a boilerplate/sample project.
 *
 * Do NOT ship this to production. For production apps consider:
 *  - Moving the key to a secure backend and calling the API from your server,
 *  - Using Android Keystore + AES to protect the key,
 *  - Issuing short-lived tokens from your server.
 */
class AuthInterceptor : Interceptor {

    companion object {
        // Hardcoded Base64-encoded API key for testing / boilerplate only.
        // WARNING: This is NOT secure.
        private const val ENCODED_API_KEY =
            "cXl2S05OM1ExREpCNzhFbnBvZ195V3JUUmhSMll1SnBPNEc5XzFVZWcwcw=="
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val key = String(Base64.decode(ENCODED_API_KEY, Base64.DEFAULT))
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Client-ID $key")
            .build()
        return chain.proceed(request)
    }
}
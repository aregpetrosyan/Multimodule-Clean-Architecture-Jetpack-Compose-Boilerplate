package com.aregyan.core.network.interceptor

import android.util.Base64
import okhttp3.Interceptor
import okhttp3.Response

/**
 * NOTE: The API key below is hardcoded and only Base64-encoded for simplicity.
 * This is included here solely for demonstration and convenience in this boilerplate project.
 *
 * In a real application, avoid hardcoding keys. Instead, consider:
 *  - Retrieving it securely from your backend,
 *  - Using Android Keystore + encryption,
 *  - Or providing it via build-time configuration (e.g., BuildConfig or environment variables).
 */
class AuthInterceptor : Interceptor {

    companion object {
        // Base64-encoded API key used for simplicity in this boilerplate project.
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
package com.aregyan.core.network.interceptor

import android.util.Base64
import com.aregyan.core.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * NOTE: The API key is provided via BuildConfig.
 *
 * In a real application, consider more robust security practices, such as:
 *  - Fetching the token securely from a backend service.
 *  - Storing it in encrypted storage or the Android Keystore.
 */
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val key = String(Base64.decode(BuildConfig.UNSPLASH_API_KEY, Base64.DEFAULT))
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Client-ID $key")
            .build()
        return chain.proceed(request)
    }
}
package com.aregyan.core.network

import com.aregyan.core.data.api.RandomResponse
import retrofit2.http.GET

fun interface RandomApiService {

    @GET("photos/random")
    suspend fun getRandomPhoto(): RandomResponse
}
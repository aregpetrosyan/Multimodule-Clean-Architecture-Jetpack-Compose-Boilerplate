package com.aregyan.feature.random.data

import retrofit2.http.GET

fun interface RandomApiService {

    @GET("photos/random")
    suspend fun getRandomPhoto(): RandomResponse
}
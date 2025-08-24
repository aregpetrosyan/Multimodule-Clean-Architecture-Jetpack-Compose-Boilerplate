package com.aregyan.core.network

import com.aregyan.core.data.api.SimilarResponse
import retrofit2.http.GET
import retrofit2.http.Query

fun interface SimilarApiService {

    @GET("search/photos")
    suspend fun getSimilarPhotos(
        @Query("query") query: String
    ): SimilarResponse
}
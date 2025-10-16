package com.aregyan.feature.similar.data

import retrofit2.http.GET
import retrofit2.http.Query

fun interface SimilarApiService {

    @GET("search/photos")
    suspend fun getSimilarPhotos(
        @Query("query") query: String
    ): SimilarResponse
}
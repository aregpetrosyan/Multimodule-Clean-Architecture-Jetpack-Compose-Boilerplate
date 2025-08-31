package com.aregyan.feature.explore

import retrofit2.http.GET
import retrofit2.http.Query

interface ExploreApiService {

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("order_by") orderBy: String = "latest" // or "popular"
    ): List<ExploreResponse>
}
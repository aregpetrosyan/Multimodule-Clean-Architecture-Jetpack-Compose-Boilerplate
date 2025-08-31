package com.aregyan.feature.similar.data

import javax.inject.Inject

class SimilarRepository @Inject constructor(
    private val apiService: SimilarApiService
) {

    suspend fun getSimilarPhotos(query: String): Result<SimilarResponse> = runCatching {
        apiService.getSimilarPhotos(query)
    }

}
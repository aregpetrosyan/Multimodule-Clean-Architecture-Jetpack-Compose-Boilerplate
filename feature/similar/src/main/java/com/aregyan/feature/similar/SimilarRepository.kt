package com.aregyan.feature.similar

import com.aregyan.core.data.api.SimilarResponse
import com.aregyan.core.network.SimilarApiService
import javax.inject.Inject

class SimilarRepository @Inject constructor(
    private val apiService: SimilarApiService
) {

    suspend fun getSimilarPhotos(query: String): Result<SimilarResponse> = runCatching {
        apiService.getSimilarPhotos(query)
    }

}
package com.aregyan.feature.random

import com.aregyan.core.data.api.RandomResponse
import com.aregyan.core.network.RandomApiService
import javax.inject.Inject

class RandomRepository @Inject constructor(
    private val apiService: RandomApiService
) {

    suspend fun loadRandomPhoto(): Result<RandomResponse> = runCatching {
        apiService.getRandomPhoto()
    }

}
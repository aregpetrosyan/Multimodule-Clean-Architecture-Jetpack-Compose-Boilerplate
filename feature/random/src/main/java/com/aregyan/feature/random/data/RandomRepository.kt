package com.aregyan.feature.random.data

import javax.inject.Inject

class RandomRepository @Inject constructor(
    private val apiService: RandomApiService
) {

    suspend fun loadRandomPhoto(): Result<RandomResponse> = runCatching {
        apiService.getRandomPhoto()
    }

}
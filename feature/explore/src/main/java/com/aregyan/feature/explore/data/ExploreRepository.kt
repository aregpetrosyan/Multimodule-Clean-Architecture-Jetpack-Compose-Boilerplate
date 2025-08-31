package com.aregyan.feature.explore.data

import javax.inject.Inject

private const val ORDER_BY_LATEST = "latest"
private const val FIRST_PAGE = 1
private const val PHOTOS_PER_PAGE = 30

class ExploreRepository @Inject constructor(
    private val apiService: ExploreApiService
) {

    suspend fun loadLatestPhotos(): Result<List<ExploreResponse>> = runCatching {
        apiService.getPhotos(FIRST_PAGE, PHOTOS_PER_PAGE, ORDER_BY_LATEST)
    }

}
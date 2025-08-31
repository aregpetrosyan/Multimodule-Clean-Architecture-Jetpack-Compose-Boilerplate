package com.aregyan.feature.explore.domain

import com.aregyan.core.domain.Photo
import com.aregyan.feature.explore.data.ExploreRepository
import com.aregyan.feature.explore.data.toDomain
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExplorePhotosUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {
    operator fun invoke(): Flow<Result<List<Photo>>> = flow {
        val result = exploreRepository.loadLatestPhotos().mapCatching { list ->
            list.toDomain()
        }
        emit(result)
    }
}
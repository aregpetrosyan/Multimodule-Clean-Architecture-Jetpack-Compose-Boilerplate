package com.aregyan.feature.explore

import com.aregyan.core.domain.Photo
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
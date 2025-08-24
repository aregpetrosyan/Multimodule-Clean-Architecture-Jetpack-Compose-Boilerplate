package com.aregyan.feature.random

import com.aregyan.core.data.apiMappers.toDomain
import com.aregyan.core.domain.Photo
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RandomPhotoUseCase @Inject constructor(
    private val exploreRepository: RandomRepository
) {
    operator fun invoke(): Flow<Result<Photo>> = flow {
        val result = exploreRepository.loadRandomPhoto().mapCatching { list ->
            list.toDomain()
        }
        emit(result)
    }
}
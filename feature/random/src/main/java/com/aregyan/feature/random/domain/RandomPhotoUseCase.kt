package com.aregyan.feature.random.domain

import com.aregyan.core.domain.Photo
import com.aregyan.feature.random.data.RandomRepository
import com.aregyan.feature.random.data.toDomain
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
package com.aregyan.feature.similar.domain

import com.aregyan.core.domain.Photo
import com.aregyan.feature.similar.data.SimilarRepository
import com.aregyan.feature.similar.data.toDomain
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SimilarPhotosUseCase @Inject constructor(
    private val similarRepository: SimilarRepository
) {
    operator fun invoke(query: String): Flow<Result<List<Photo>>> = flow {
        val result = similarRepository.getSimilarPhotos(query).mapCatching { list ->
            list.toDomain()
        }
        emit(result)
    }
}
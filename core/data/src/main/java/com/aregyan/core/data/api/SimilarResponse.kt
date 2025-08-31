package com.aregyan.core.data.api

import com.aregyan.core.data.Links
import com.aregyan.core.data.Urls
import com.aregyan.core.data.User
import com.squareup.moshi.Json

data class SimilarResponse(
    val total: Int,
    @Json(name = "total_pages") val totalPages: Int,
    val results: List<Photo>
)

data class Photo(
    val id: String,
    @Json(name = "created_at") val createdAt: String,
    val width: Int,
    val height: Int,
    val color: String?,
    @Json(name = "blur_hash") val blurHash: String?,
    val likes: Int,
    @Json(name = "liked_by_user") val likedByUser: Boolean,
    val description: String?,
    val user: User,
    @Json(name = "current_user_collections") val currentUserCollections: List<Any>,
    val urls: Urls,
    val links: Links
)

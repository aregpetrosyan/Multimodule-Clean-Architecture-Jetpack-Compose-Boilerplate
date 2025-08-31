package com.aregyan.feature.random.data

import com.aregyan.core.data.Links
import com.aregyan.core.data.Urls
import com.aregyan.core.data.User
import com.squareup.moshi.Json

data class RandomResponse(
    val id: String,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "updated_at") val updatedAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    @Json(name = "blur_hash") val blurHash: String,
    val downloads: Int,
    val likes: Int,
    @Json(name = "liked_by_user") val likedByUser: Boolean,
    val description: String?,
    val exif: Exif?,
    val location: Location?,
    @Json(name = "current_user_collections") val currentUserCollections: List<Collection>,
    val urls: Urls,
    val links: Links,
    val user: User
)

data class Exif(
    val make: String?,
    val model: String?,
    @Json(name = "exposure_time") val exposureTime: String?,
    val aperture: String?,
    @Json(name = "focal_length") val focalLength: String?,
    val iso: Int?
)

data class Location(
    val name: String?,
    val city: String?,
    val country: String?,
    val position: Position?
)

data class Position(
    val latitude: Double?,
    val longitude: Double?
)

data class Collection(
    val id: Int,
    val title: String,
    @Json(name = "published_at") val publishedAt: String,
    @Json(name = "last_collected_at") val lastCollectedAt: String,
    @Json(name = "updated_at") val updatedAt: String,
    @Json(name = "cover_photo") val coverPhoto: Any?,
    val user: Any?
)

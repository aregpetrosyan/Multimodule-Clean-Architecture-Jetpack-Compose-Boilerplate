package com.aregyan.core.data.api

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

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class Links(
    val self: String,
    val html: String,
    val download: String,
    @Json(name = "download_location") val downloadLocation: String
)

data class User(
    val id: String,
    @Json(name = "updated_at") val updatedAt: String,
    val username: String,
    val name: String,
    @Json(name = "portfolio_url") val portfolioUrl: String?,
    val bio: String?,
    val location: String?,
    @Json(name = "total_likes") val totalLikes: Int,
    @Json(name = "total_photos") val totalPhotos: Int,
    @Json(name = "total_collections") val totalCollections: Int,
    @Json(name = "instagram_username") val instagramUsername: String?,
    @Json(name = "twitter_username") val twitterUsername: String?,
    val links: UserLinks
)

data class UserLinks(
    val self: String,
    val html: String,
    val photos: String,
    val likes: String,
    val portfolio: String
)

package com.aregyan.core.data

import com.squareup.moshi.Json

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
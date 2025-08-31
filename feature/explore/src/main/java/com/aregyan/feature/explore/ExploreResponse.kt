package com.aregyan.core.data.api

import com.squareup.moshi.Json

data class ExploreResponse(
    val id: String,
    val description: String?,
    @Json(name = "alt_description") val altDescription: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashUser
)

data class UnsplashPhotoUrls(
    val small: String,
    val regular: String,
    val full: String
)

data class UnsplashUser(
    val name: String,
    @Json(name = "profile_image") val profileImage: ProfileImage
)

data class ProfileImage(
    val small: String
)
package com.aregyan.core.navigation

import androidx.navigation3.runtime.NavKey
import com.aregyan.core.domain.Photo
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object Explore : NavKey

    @Serializable
    object Random : NavKey

    @Serializable
    object Favorites : NavKey

    @Serializable
    data class Similar(@Contextual val photo: Photo) : NavKey
}

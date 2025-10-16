package com.aregyan.core.navigation

import androidx.navigation3.runtime.NavKey
import com.aregyan.core.domain.Photo
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

sealed class Routes : NavKey {
    @Serializable
    object Explore : Routes()

    @Serializable
    object Random : Routes()

    @Serializable
    object Favorites : Routes()

    @Serializable
    data class Similar(@Contextual val photo: Photo) : Routes()
}

package com.aregyan.core.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import com.aregyan.feature.explore.ExploreScreen
import com.aregyan.feature.favorites.FavoritesScreen
import com.aregyan.feature.random.RandomScreen
import com.aregyan.feature.similar.SimilarScreen

fun appNavEntryProvider(key: NavKey, backStack: NavBackStack): NavEntry<NavKey> =
    when (key) {
        is Routes.Explore -> NavEntry(key) {
            ExploreScreen(
                onSimilarClick = { photo ->
                    backStack.add(Routes.Similar(photo))
                }
            )
        }

        is Routes.Random -> NavEntry(key) { RandomScreen() }
        is Routes.Favorites -> NavEntry(key) {
            FavoritesScreen(
                onSimilarClick = { photo ->
                    backStack.add(Routes.Similar(photo))
                }
            )
        }
        is Routes.Similar -> NavEntry(key) { SimilarScreen(photo = key.photo) }
        else -> error("Unknown route: $key")
    }
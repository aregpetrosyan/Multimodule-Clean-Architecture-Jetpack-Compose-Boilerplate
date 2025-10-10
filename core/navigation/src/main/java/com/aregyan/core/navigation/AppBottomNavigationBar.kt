package com.aregyan.core.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Composable
fun AppBottomNavigationBar(
    backStack: NavBackStack<NavKey>
) {
    val tabs = listOf(
        Routes.Explore to R.drawable.explore_24dp,
        Routes.Random to R.drawable.celebration_24dp,
        Routes.Favorites to R.drawable.favorite_24dp
    )

    val currentRoute = backStack.lastOrNull()
    val showBottomBar = tabs.any { it.first == currentRoute }

    if (!showBottomBar) return

    NavigationBar {
        tabs.forEach { (route, iconRes) ->
            val isSelected = currentRoute == route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        backStack.removeLastUntil { it == Routes.Explore || it == Routes.Random || it == Routes.Favorites }
                        if (backStack.lastOrNull() != route) {
                            backStack.add(route)
                        }
                    }
                },
                icon = {
                    Icon(painterResource(iconRes), contentDescription = null)
                }
            )
        }
    }
}

// Extension for cleaner back stack manipulation
fun NavBackStack<NavKey>.removeLastUntil(predicate: (NavKey) -> Boolean) {
    while (lastOrNull()?.let { !predicate(it) } == true) {
        removeLastOrNull()
    }
}
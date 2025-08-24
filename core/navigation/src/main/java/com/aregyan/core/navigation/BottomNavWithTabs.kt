// File: com/aregyan/architecture/BottomNavWithTabs.kt
package com.aregyan.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

@Composable
fun BottomNavWithTabs() {
    val backStack = rememberNavBackStack(Routes.Explore)

    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(backStack = backStack)
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            backStack = backStack,
            entryProvider = { key -> appNavEntryProvider(key, backStack) }
        )
    }
}
package com.aregyan.architecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aregyan.architecture.ui.theme.MultimoduleCleanArchitectureJetpackComposeTheme
import com.aregyan.core.navigation.BottomNavWithTabs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultimoduleCleanArchitectureJetpackComposeTheme {
                BottomNavWithTabs()
            }
        }
    }
}
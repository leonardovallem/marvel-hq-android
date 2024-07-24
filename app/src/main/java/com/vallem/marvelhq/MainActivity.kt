package com.vallem.marvelhq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vallem.marvelhq.list.presentation.ComicsListScreen
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            MarvelHQTheme {
                NavHost(navController, startDestination = ComicsListScreen) {
                    composable<ComicsListScreen> {
                        ComicsListScreen()
                    }
                }
            }
        }
    }
}

package com.vallem.marvelhq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vallem.marvelhq.details.presentation.ComicDetailsScreen
import com.vallem.marvelhq.favorites.presentation.FavoriteComicsScreen
import com.vallem.marvelhq.list.presentation.ComicsListScreen
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            MarvelHQTheme {
                SharedTransitionLayout {
                    NavHost(navController, startDestination = ComicsListScreen) {
                        composable<ComicsListScreen> {
                            ComicsListScreen(navController, this, this@SharedTransitionLayout)
                        }

                        composable<FavoriteComicsScreen> {
                            FavoriteComicsScreen()
                        }

                        composable<Comic> {
                            val comic = it.toRoute<Comic>()
                            ComicDetailsScreen(
                                comic = comic,
                                navController = navController,
                                animatedContentScope = this,
                                sharedTransitionScope = this@SharedTransitionLayout,
                            )
                        }
                    }
                }
            }
        }
    }
}

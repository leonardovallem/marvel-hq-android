package com.vallem.marvelhq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vallem.marvelhq.details.presentation.ComicDetailsScreen
import com.vallem.marvelhq.favorites.presentation.FavoriteComicsScreen
import com.vallem.marvelhq.list.presentation.ComicsListScreen
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.presentation.AppRoute
import com.vallem.marvelhq.shared.presentation.icon
import com.vallem.marvelhq.shared.presentation.label
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            var currentRoute by rememberSaveable(saver = AppRoute.Saver) {
                mutableStateOf(AppRoute.ComicsList)
            }

            MarvelHQTheme {
                SharedTransitionLayout {
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                AppRoute.entries.forEach {
                                    NavigationBarItem(
                                        selected = it == currentRoute,
                                        onClick = {
                                            navController.navigate(it)
                                            currentRoute = it
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = it.icon,
                                                contentDescription = null,
                                            )
                                        },
                                        label = { Text(text = it.label) },
                                    )
                                }
                            }
                        }
                    ) {
                        NavHost(navController, startDestination = AppRoute.ComicsList) {
                            composable<AppRoute.ComicsList> {
                                ComicsListScreen(
                                    navController = navController,
                                    animatedContentScope = this,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                )
                            }

                            composable<AppRoute.FavoriteComics> {
                                FavoriteComicsScreen(
                                    navController = navController,
                                    animatedContentScope = this,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                )
                            }

                            composable<Comic> {
                                ComicDetailsScreen(
                                    comic = it.toRoute<Comic>(),
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
}

package com.vallem.marvelhq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vallem.marvelhq.details.presentation.ComicDetailsScreen
import com.vallem.marvelhq.list.presentation.ComicsListViewModel
import com.vallem.marvelhq.list.presentation.FavoriteComicsViewModel
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.presentation.AppRoute
import com.vallem.marvelhq.shared.presentation.component.ComicsListScreenContent
import com.vallem.marvelhq.shared.presentation.extension.ifTargetIs
import com.vallem.marvelhq.shared.presentation.icon
import com.vallem.marvelhq.shared.presentation.label
import com.vallem.marvelhq.ui.theme.MarvelHQTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setTransparentWindowBackground()

        setContent {
            val navController = rememberNavController()
            var currentRoute by rememberSaveable(saver = AppRoute.Saver) {
                mutableStateOf(AppRoute.ComicsList)
            }

            MarvelHQTheme {
                SharedTransitionLayout {
                    Column(modifier = Modifier.fillMaxSize()) {
                        NavHost(
                            navController = navController,
                            startDestination = AppRoute.ComicsList,
                            modifier = Modifier.weight(1f)
                        ) {
                            composable<AppRoute.ComicsList>(
                                enterTransition = {
                                    slideInHorizontally { it }
                                        .ifTargetIs<AppRoute.FavoriteComics>(targetState.destination)
                                },
                                exitTransition = {
                                    slideOutHorizontally { -it }
                                        .ifTargetIs<AppRoute.FavoriteComics>(targetState.destination)
                                },
                                popEnterTransition = {
                                    slideInHorizontally { -it }
                                        .ifTargetIs<AppRoute.FavoriteComics>(targetState.destination)
                                },
                                popExitTransition = {
                                    slideOutHorizontally { it }
                                        .ifTargetIs<AppRoute.FavoriteComics>(targetState.destination)
                                },
                            ) {
                                val viewModel =
                                    koinViewModel<ComicsListViewModel>(key = "COMICS_LIST_VIEWMODEL")

                                ComicsListScreenContent(
                                    title = "HQs",
                                    comics = viewModel.comics,
                                    filters = viewModel.filters,
                                    appendState = viewModel.appendState,
                                    refreshState = viewModel.refreshState,
                                    retryPagination = viewModel::retry,
                                    onFiltersChange = viewModel::updateFilters,
                                    loadNextPage = viewModel::loadNextPage,
                                    onComicClick = { navController.navigate(it) },
                                    animatedContentScope = this,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                )
                            }

                            composable<AppRoute.FavoriteComics>(
                                enterTransition = {
                                    slideInHorizontally { it }
                                        .ifTargetIs<AppRoute.ComicsList>(targetState.destination)
                                },
                                exitTransition = {
                                    slideOutHorizontally { -it }
                                        .ifTargetIs<AppRoute.ComicsList>(targetState.destination)
                                },
                                popEnterTransition = {
                                    slideInHorizontally { -it }
                                        .ifTargetIs<AppRoute.ComicsList>(targetState.destination)
                                },
                                popExitTransition = {
                                    slideOutHorizontally { it }
                                        .ifTargetIs<AppRoute.ComicsList>(targetState.destination)
                                },
                            ) {
                                val viewModel =
                                    koinViewModel<FavoriteComicsViewModel>(key = "FAVORITE_COMICS_VIEWMODEL")

                                ComicsListScreenContent(
                                    title = "Favoritos",
                                    comics = viewModel.comics,
                                    filters = viewModel.filters,
                                    appendState = viewModel.appendState,
                                    refreshState = viewModel.refreshState,
                                    retryPagination = viewModel::retry,
                                    loadNextPage = viewModel::loadNextPage,
                                    onFiltersChange = viewModel::updateFilters,
                                    onComicClick = { navController.navigate(it) },
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

                        NavigationBar {
                            AppRoute.entries.forEach {
                                NavigationBarItem(
                                    selected = it == currentRoute,
                                    onClick = {
                                        val screenAlreadyInStack =
                                            navController.popBackStack(it, false)
                                        if (!screenAlreadyInStack) navController.navigate(it)
                                        currentRoute = it
                                    },
                                    icon = {
                                        Icon(imageVector = it.icon, contentDescription = null)
                                    },
                                    label = { Text(text = it.label) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setTransparentWindowBackground() {
        window.decorView.post {
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }
}

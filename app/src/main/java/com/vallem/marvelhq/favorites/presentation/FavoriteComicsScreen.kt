package com.vallem.marvelhq.favorites.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.vallem.marvelhq.shared.presentation.component.ComicsListScreenContent
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object FavoriteComicsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FavoriteComicsScreen(
    navController: NavHostController,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    viewModel: FavoriteComicsViewModel = koinViewModel(),
) {
    ComicsListScreenContent(
        comics = viewModel.comics,
        appendState = viewModel.appendState,
        refreshState = viewModel.refreshState,
        retryPagination = viewModel::retry,
        loadNextPage = viewModel::loadNextPage,
        onComicClick = { navController.navigate(it) },
        animatedContentScope = animatedContentScope,
        sharedTransitionScope = sharedTransitionScope,
    )
}

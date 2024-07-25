package com.vallem.marvelhq.list.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.vallem.marvelhq.shared.presentation.component.ComicsListScreenContent
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ComicsListScreen(
    navController: NavHostController,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    viewModel: ComicsListViewModel = koinViewModel(),
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

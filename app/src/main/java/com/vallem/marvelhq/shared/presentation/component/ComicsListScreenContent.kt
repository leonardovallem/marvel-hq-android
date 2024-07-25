package com.vallem.marvelhq.shared.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vallem.marvelhq.list.presentation.component.ComicCard
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.presentation.pagination.PaginationState
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ComicsListScreenContent(
    comics: SnapshotStateList<Comic>,
    appendState: PaginationState.Append,
    refreshState: PaginationState.Refresh,
    retryPagination: () -> Unit,
    loadNextPage: () -> Unit,
    onComicClick: (Comic) -> Unit,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    val configuration = LocalConfiguration.current
    val columns = configuration.screenWidthDp / ComicScreenDividerFactor

    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyGridState()

    LaunchedEffect(appendState) {
        if (appendState == PaginationState.Append.Error) {
            val result = snackbarHostState.showSnackbar(
                message = "Erro ao carregar mais items",
                actionLabel = "Tentar novamente",
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.ActionPerformed) retryPagination()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { pv ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .padding(pv)
                .fillMaxSize()
        ) {
            when (refreshState) {
                PaginationState.Refresh.NotLoading -> {
                    if (comics.size > 0) {
                        items(
                            items = comics,
                            key = { it.id },
                            contentType = { "COMIC" }
                        ) {
                            ComicCard(
                                comic = it,
                                onClick = { onComicClick(it) },
                                animatedContentScope = animatedContentScope,
                                sharedTransitionScope = sharedTransitionScope,
                            )
                        }

                        item(span = { GridItemSpan(maxLineSpan) }) {
                            LaunchedEffect(Unit) {
                                loadNextPage()
                            }
                        }
                    } else item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Nenhum resultado encontrado",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                PaginationState.Refresh.Error -> item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(text = "Erro")
                }

                PaginationState.Refresh.Loading -> items(
                    count = LoadingComicsCount,
                    contentType = { "COMIC_SKELETON" },
                ) {
                    ComicCard.Skeleton()
                }
            }

            if (appendState == PaginationState.Append.Loading) item(
                key = "LOADING_INDICATOR",
                span = { GridItemSpan(maxLineSpan) },
            ) {
                PageLoadingIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

private const val ComicScreenDividerFactor = 180
private const val LoadingComicsCount = 20

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun ComicsListScreenPreview() {
    MarvelHQTheme {
        SharedTransitionLayout {
            AnimatedContent(true) {
                if (it) ComicsListScreenContent(
                    comics = remember { List(10) { mockComic() }.toMutableStateList() },
                    appendState = PaginationState.Append.NotLoading,
                    refreshState = PaginationState.Refresh.NotLoading,
                    retryPagination = {},
                    loadNextPage = {},
                    onComicClick = {},
                    animatedContentScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout,
                )
            }
        }
    }
}


private fun mockComic() = Comic(
    id = 1689,
    title = "Official Handbook of the Marvel Universe (2004) #10 (MARVEL KNIGHTS)",
    description = "Official Handbook of the Marvel Universe",
    thumbnailUrl = "http://i.annihil.us/u/prod/marvel/i/mg/9/30/4bc64df4105b9.jpg",
)

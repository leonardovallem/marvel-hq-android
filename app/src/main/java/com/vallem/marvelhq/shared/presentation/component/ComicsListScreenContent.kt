package com.vallem.marvelhq.shared.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vallem.marvelhq.list.presentation.component.ComicCard
import com.vallem.marvelhq.list.presentation.component.ComicsFilters
import com.vallem.marvelhq.list.presentation.model.ComicsListFilters
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.presentation.pagination.PaginationState
import com.vallem.marvelhq.shared.presentation.util.LocalFocusClearer
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ComicsListScreenContent(
    title: String,
    comics: SnapshotStateList<Comic>,
    filters: ComicsListFilters,
    appendState: PaginationState.Append,
    refreshState: PaginationState.Refresh,
    retryPagination: () -> Unit,
    refresh: () -> Unit,
    onFiltersChange: (ComicsListFilters) -> Unit,
    loadNextPage: () -> Unit,
    onComicClick: (Comic) -> Unit,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    val configuration = LocalConfiguration.current
    val focusClearer = LocalFocusClearer.current
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
                .semantics {
                    testTag = "comicsGrid"
                    set(ItemsPerRowSemanticProperty, columns.toString())
                }
                .padding(pv)
                .fillMaxSize()
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) },
                contentType = { "TITLE" },
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            when (refreshState) {
                PaginationState.Refresh.NotLoading -> {
                    item(
                        span = { GridItemSpan(maxLineSpan) },
                        contentType = { "FILTERS" },
                    ) {
                        ComicsFilters(
                            filters = filters,
                            onChange = {
                                focusClearer.clear()
                                onFiltersChange(it)
                            },
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }

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

                        if (appendState != PaginationState.Append.EndReached) item(
                            span = { GridItemSpan(maxLineSpan) },
                        ) {
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
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.WarningAmber,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(64.dp)
                        )

                        Text(
                            text = "Ops... Aconteceu algum erro por aqui.",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )

                        FilledTonalButton(onClick = refresh) {
                            Text(text = "Tentar novamente")
                        }
                    }
                }

                PaginationState.Refresh.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ComicsFilters.Skeleton()
                    }

                    items(
                        count = LoadingComicsCount,
                        contentType = { "COMIC_SKELETON" },
                    ) {
                        ComicCard.Skeleton()
                    }
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

val ItemsPerRowSemanticProperty = SemanticsPropertyKey<String>("ItemsPerRow")
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
                    title = "HQs",
                    comics = remember { List(10) { mockComic() }.toMutableStateList() },
                    filters = ComicsListFilters(),
                    appendState = PaginationState.Append.NotLoading,
                    refreshState = PaginationState.Refresh.NotLoading,
                    retryPagination = {},
                    refresh = {},
                    onFiltersChange = {},
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
    releaseDate = null,
)

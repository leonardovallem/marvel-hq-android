package com.vallem.marvelhq.list.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.vallem.marvelhq.list.presentation.component.ComicCard
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.ui.theme.MarvelHQTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object ComicsListScreen

@Composable
fun ComicsListScreen(viewModel: ComicsListViewModel = koinViewModel()) {
    ComicsListScreenContent(comics = viewModel.comics.collectAsLazyPagingItems())
}

@Composable
private fun ComicsListScreenContent(comics: LazyPagingItems<Comic>) {
    val configuration = LocalConfiguration.current
    val columns = configuration.screenWidthDp / ComicScreenDividerFactor

    Scaffold { pv ->
        Column(modifier = Modifier.padding(pv)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                when (val a = comics.loadState.refresh) {
                    is LoadState.NotLoading -> items(comics.itemCount) {
                        comics[it]?.let { comic -> ComicCard(comic = comic) }
                    }

                    is LoadState.Error -> item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(text = a.error.message.toString())
                    }

                    LoadState.Loading -> items(LoadingComicsCount) {
                        ComicCard.Skeleton()
                    }
                }

                if (comics.loadState.append == LoadState.Loading) item(
                    key = "LOADING_INDICATOR",
                    span = { GridItemSpan(maxLineSpan) },
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

private const val ComicScreenDividerFactor = 180
private const val LoadingComicsCount = 20

@Preview
@Composable
private fun ComicsListScreenPreview() {
    MarvelHQTheme {
        ComicsListScreenContent(ComicsListViewModel.comics.collectAsLazyPagingItems())
    }
}
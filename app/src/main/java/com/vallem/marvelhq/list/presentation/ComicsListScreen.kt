package com.vallem.marvelhq.list.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vallem.marvelhq.list.presentation.component.ComicCard
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.ui.theme.MarvelHQTheme
import kotlinx.serialization.Serializable

@Serializable
object ComicsListScreen

@Composable
fun ComicsListScreen() {
    ComicsListScreenContent(comics = ComicsListViewModel.comics)
}

@Composable
private fun ComicsListScreenContent(comics: SnapshotStateList<Comic>) {
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
                items(comics) {
                    ComicCard(comic = it)
                }
            }
        }
    }
}

private const val ComicScreenDividerFactor = 180

@Preview
@Composable
private fun ComicsListScreenPreview() {
    MarvelHQTheme {
        ComicsListScreenContent(ComicsListViewModel.comics)
    }
}
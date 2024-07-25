package com.vallem.marvelhq.favorites.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vallem.marvelhq.ui.theme.MarvelHQTheme
import kotlinx.serialization.Serializable

@Serializable
object FavoriteComicsScreen

@Composable
fun FavoriteComicsScreen() {

}

@Preview
@Composable
private fun FavoriteComicsScreenPreview() {
    MarvelHQTheme {
        FavoriteComicsScreen()
    }
}

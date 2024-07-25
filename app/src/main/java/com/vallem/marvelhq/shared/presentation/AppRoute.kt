package com.vallem.marvelhq.shared.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.LibraryBooks
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import kotlinx.serialization.Serializable

sealed interface AppRoute {
    @Serializable
    data object ComicsList : AppRoute

    @Serializable
    data object FavoriteComics : AppRoute

    val name
        get() = when (this) {
            ComicsList -> "ComicsList"
            FavoriteComics -> "FavoriteComics"
        }

    companion object {
        val entries by lazy { listOf(ComicsList, FavoriteComics) }

        operator fun get(name: String) = when (name) {
            "ComicsList" -> ComicsList
            "FavoriteComics" -> FavoriteComics
            else -> null
        }

        val Saver = Saver<MutableState<AppRoute>, String>(
            save = { it.value.name },
            restore = { mutableStateOf(get(it)!!) },
        )
    }
}

val AppRoute.icon
    get() = when (this) {
        AppRoute.ComicsList -> Icons.AutoMirrored.Rounded.LibraryBooks
        AppRoute.FavoriteComics -> Icons.Rounded.Favorite
    }

val AppRoute.label
    get() = when (this) {
        AppRoute.ComicsList -> "HQs"
        AppRoute.FavoriteComics -> "Favoritos"
    }
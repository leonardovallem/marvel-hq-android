package com.vallem.marvelhq.list.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.SortByAlpha

data class ComicsListFilters(
    val title: String = "",
    val order: ComicsListSorting = ComicsListSorting(),
)

data class ComicsListSorting(
    val sortBy: ComicsSortOrder = ComicsSortOrder.Title,
    val descending: Boolean = false,
)

enum class ComicsSortOrder {
    Title, ReleaseDate
}

val ComicsSortOrder.label
    get() = when (this) {
        ComicsSortOrder.Title -> "Título"
        ComicsSortOrder.ReleaseDate -> "Data de lançamento"
    }

val ComicsSortOrder.icon
    get() = when (this) {
        ComicsSortOrder.Title -> Icons.Rounded.SortByAlpha
        ComicsSortOrder.ReleaseDate -> Icons.Rounded.Event
    }
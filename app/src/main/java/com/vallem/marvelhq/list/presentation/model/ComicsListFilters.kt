package com.vallem.marvelhq.list.presentation.model

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
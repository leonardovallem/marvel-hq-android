package com.vallem.marvelhq.list.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.SortByAlpha

val ComicsListFilters.queryParams
    get() = "orderBy=${order.queryValue}".let {
        if (title.isNotBlank()) it + "&titleStartsWith=${title.trim()}"
        else it
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

val ComicsListSorting.queryValue
    get() = when (sortBy) {
        ComicsSortOrder.Title -> "title"
        ComicsSortOrder.ReleaseDate -> "focDate"
    }.let { if (descending) "-$it" else it }
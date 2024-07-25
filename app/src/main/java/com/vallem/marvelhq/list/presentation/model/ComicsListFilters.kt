package com.vallem.marvelhq.list.presentation.model

data class ComicsListFilters(
    val title: String = "",
    val order: ComicsListOrder = ComicsListOrder.ReleaseDate(true)
)

sealed class ComicsListOrder(open val descending: Boolean) {
    data class Title(override val descending: Boolean) : ComicsListOrder(descending)
    data class ReleaseDate(override val descending: Boolean) : ComicsListOrder(descending)
}
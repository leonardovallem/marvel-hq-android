package com.vallem.marvelhq.shared.presentation.pagination

sealed interface PaginationState {
    enum class Refresh : PaginationState {
        NotLoading, Loading, Error
    }

    enum class Append : PaginationState {
        NotLoading, Loading, Error, EndReached
    }
}
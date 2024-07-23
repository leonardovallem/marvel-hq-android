package com.vallem.marvelhq.shared.presentation.pagination

sealed class PaginationResult<out T>(open val isRefresh: Boolean) {
    data class Failure(val throwable: Throwable, override val isRefresh: Boolean) : PaginationResult<Nothing>(isRefresh)
    data class Success<T>(val data: List<T>, override val isRefresh: Boolean) : PaginationResult<T>(isRefresh)
}

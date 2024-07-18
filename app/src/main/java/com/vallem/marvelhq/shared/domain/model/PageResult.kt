package com.vallem.marvelhq.shared.domain.model

sealed interface PageResult<out T> {
    data class Failure(val error: Throwable) : PageResult<Nothing>
    data class Success<T>(val data: List<T>, val page: Int, val total: Int) : PageResult<T>
}

val PageResult<*>.isSuccess get() = this is PageResult.Success<*>

fun <T> PageResult<T>.getOrNull() = this as? PageResult.Success<T>

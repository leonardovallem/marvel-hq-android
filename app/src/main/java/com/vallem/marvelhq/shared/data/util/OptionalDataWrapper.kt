package com.vallem.marvelhq.shared.data.util

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map

/**
 * Helper class to overcome the lack of a PagingData.mapNotNull method
 */
sealed interface OptionalDataWrapper<out T> {
    data class Value<T : Any>(val value: T) : OptionalDataWrapper<T>
    data object Empty : OptionalDataWrapper<Nothing>

    companion object {
        fun <T> from(value: T) = if (value == null) Empty else Value(value)
    }
}

fun OptionalDataWrapper<*>.hasValue() = this is OptionalDataWrapper.Value

fun <T : Any, R> PagingData<T>.mapNotNull(transform: (T) -> R) =map {
    OptionalDataWrapper.from(transform(it))
}
    .filter { it.hasValue() }
    .map { (it as OptionalDataWrapper.Value).value }
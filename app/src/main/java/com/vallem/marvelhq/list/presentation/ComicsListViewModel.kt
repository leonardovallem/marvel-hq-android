package com.vallem.marvelhq.list.presentation

import androidx.compose.runtime.mutableStateListOf
import com.vallem.marvelhq.shared.AppConstants.DefaultPageSize
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.repository.ComicsRepository
import com.vallem.marvelhq.shared.domain.repository.FavoriteComicsRepository
import com.vallem.marvelhq.shared.presentation.pagination.PaginatedViewModel
import com.vallem.marvelhq.shared.presentation.pagination.PaginationResult

class ComicsListViewModel(
    private val repository: ComicsRepository,
) : PaginatedViewModel(initialPage = InitialPage) {
    val comics = mutableStateListOf<Comic>()

    init {
        loadNextPage()
    }

    override suspend fun retrieveData(): Boolean {
        val result = repository.loadPage(currentPage, DefaultPageSize, InitialPage)
        if (result.isRefresh) comics.clear()

        return when (result) {
            is PaginationResult.Success -> {
                val unique = result.data.filter { it !in comics }
                comics.addAll(unique)
                true
            }

            is PaginationResult.Failure -> false
        }
    }
}

class FavoriteComicsViewModel(
    private val repository: FavoriteComicsRepository,
) : PaginatedViewModel(InitialPage) {
    val comics = mutableStateListOf<Comic>()

    init {
        loadNextPage()
    }

    override suspend fun retrieveData(): Boolean {
        val result = repository.loadPage(currentPage, DefaultPageSize, InitialPage)
        if (result.isRefresh) comics.clear()

        return when (result) {
            is PaginationResult.Success -> {
                val unique = result.data.filter { it !in comics }
                comics.addAll(unique)
                true
            }

            is PaginationResult.Failure -> false
        }
    }
}

private const val InitialPage = 1

package com.vallem.marvelhq.list.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vallem.marvelhq.list.presentation.model.ComicsListFilters
import com.vallem.marvelhq.shared.AppConstants.DefaultPageSize
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.repository.ComicsRepository
import com.vallem.marvelhq.shared.domain.repository.FavoriteComicsRepository
import com.vallem.marvelhq.shared.presentation.pagination.PaginatedViewModel
import com.vallem.marvelhq.shared.presentation.pagination.PaginationResult
import com.vallem.marvelhq.shared.presentation.pagination.PaginationState

class ComicsListViewModel(
    private val repository: ComicsRepository,
) : PaginatedViewModel(initialPage = InitialPage) {
    val comics = mutableStateListOf<Comic>()
    var filters by mutableStateOf(ComicsListFilters())
        private set

    init {
        loadNextPage()
    }

    fun updateFilters(filters: ComicsListFilters) {
        this.filters = filters
        refresh()
    }

    override suspend fun retrieveData(page: Int): Boolean {
        val result = repository.loadPage(filters, page, DefaultPageSize, InitialPage)
        if (result.isRefresh) comics.clear()

        return when (result) {
            is PaginationResult.Success -> {
                if (result.data.size < DefaultPageSize) appendState = PaginationState.Append.EndReached
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
    var filters by mutableStateOf(ComicsListFilters())
        private set

    init {
        loadNextPage()
    }

    fun updateFilters(filters: ComicsListFilters) {
        this.filters = filters
        refresh()
    }

    override suspend fun retrieveData(page: Int): Boolean {
        val result = repository.loadPage(filters, page, DefaultPageSize, InitialPage)
        if (result.isRefresh) comics.clear()

        return when (result) {
            is PaginationResult.Success -> {
                if (result.data.size < DefaultPageSize) appendState = PaginationState.Append.EndReached
                val unique = result.data.filter { it !in comics }
                comics.addAll(unique)
                true
            }

            is PaginationResult.Failure -> false
        }
    }
}

private const val InitialPage = 1

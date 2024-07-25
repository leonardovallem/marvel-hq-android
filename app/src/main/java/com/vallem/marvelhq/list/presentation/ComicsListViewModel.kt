package com.vallem.marvelhq.list.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import com.vallem.marvelhq.shared.AppConstants.DefaultPageSize
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.repository.ComicsRepository
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

    companion object {
        fun mockComic() = Comic(
            id = 1689,
            title = "Official Handbook of the Marvel Universe (2004) #10 (MARVEL KNIGHTS)",
            description = "Official Handbook of the Marvel Universe",
            thumbnailUrl = "http://i.annihil.us/u/prod/marvel/i/mg/9/30/4bc64df4105b9.jpg",
        )

        val comics = List(10) { mockComic() }.toMutableStateList()

        private const val InitialPage = 1
    }
}

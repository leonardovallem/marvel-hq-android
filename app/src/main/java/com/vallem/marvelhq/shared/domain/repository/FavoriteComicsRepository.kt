package com.vallem.marvelhq.shared.domain.repository

import com.vallem.marvelhq.list.presentation.model.ComicsListFilters
import com.vallem.marvelhq.shared.domain.exception.ComicNotFavoritedError
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.presentation.pagination.PaginationResult

interface FavoriteComicsRepository {
    suspend fun save(comic: Comic): Result<Unit>
    suspend fun retrieve(id: Int): Result<Comic?>
    suspend fun loadPage(filters: ComicsListFilters, page: Int, size: Int, initialPage: Int): PaginationResult<Comic>

    /**
     * @throws ComicNotFavoritedError
     */
    suspend fun remove(comic: Comic): Result<Unit>
    suspend fun removeAll(): Result<Unit>
}
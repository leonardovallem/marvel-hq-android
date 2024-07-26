package com.vallem.marvelhq.shared.domain.repository.mock

import com.vallem.marvelhq.list.presentation.model.ComicsListFilters
import com.vallem.marvelhq.shared.domain.exception.ComicNotFavoritedError
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.repository.FavoriteComicsRepository
import com.vallem.marvelhq.shared.domain.repository.util.generate
import com.vallem.marvelhq.shared.domain.repository.util.randomComic
import com.vallem.marvelhq.shared.presentation.pagination.PaginationResult

class MockFavoriteComicsRepository : FavoriteComicsRepository {
    private val favorites = generate(16) { randomComic() }.toMutableList()

    override suspend fun save(comic: Comic): Result<Unit> = runCatching { favorites.add(comic) }

    override suspend fun retrieve(id: Int) = runCatching { favorites.find { it.id == id } }

    override suspend fun loadPage(
        filters: ComicsListFilters,
        page: Int,
        size: Int,
        initialPage: Int
    ) =
        if (page == initialPage) PaginationResult.Success(favorites.take(size), true)
        else PaginationResult.Failure(Throwable(), true)


    override suspend fun remove(comic: Comic): Result<Unit> = runCatching {
        val index = favorites.indexOf(comic)
        if (index < 0) throw ComicNotFavoritedError(comic)

        favorites.removeAt(index)
    }

    override suspend fun removeAll() = runCatching { favorites.clear() }
}
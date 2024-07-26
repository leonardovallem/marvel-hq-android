package com.vallem.marvelhq.shared.data.repository

import com.vallem.marvelhq.list.presentation.model.ComicsListFilters
import com.vallem.marvelhq.list.presentation.model.ComicsListSorting
import com.vallem.marvelhq.list.presentation.model.ComicsSortOrder
import com.vallem.marvelhq.shared.data.local.FavoriteComicsDao
import com.vallem.marvelhq.shared.data.local.entity.FavoriteComicEntity
import com.vallem.marvelhq.shared.data.mapper.toDomain
import com.vallem.marvelhq.shared.data.mapper.toFavoriteComicEntity
import com.vallem.marvelhq.shared.domain.exception.ComicNotFavoritedError
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.repository.FavoriteComicsRepository
import com.vallem.marvelhq.shared.presentation.pagination.PaginationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomFavoriteComicsRepository(private val dao: FavoriteComicsDao) : FavoriteComicsRepository {
    override suspend fun save(comic: Comic) = withContext(Dispatchers.IO) {
        runCatching {
            dao.save(comic.toFavoriteComicEntity())
        }
    }

    override suspend fun retrieve(id: Int) = withContext(Dispatchers.IO) {
        runCatching {
            dao.find(id)?.let(FavoriteComicEntity::toDomain)
        }
    }

    override suspend fun loadPage(filters: ComicsListFilters, page: Int, size: Int, initialPage: Int) =
        withContext(Dispatchers.IO) {
            try {
                dao
                    .retrieveAll(filters.title, filters.order.sql, size, (page - initialPage) * size)
                    .map(FavoriteComicEntity::toDomain)
                    .let { PaginationResult.Success(it, page == initialPage) }
            } catch (t: Throwable) {
                PaginationResult.Failure(t, page == initialPage)
            }
        }

    override suspend fun remove(comic: Comic) = withContext(Dispatchers.IO) {
        runCatching {
            if (dao.find(comic.id) == null) throw ComicNotFavoritedError(comic)
            dao.remove(comic.toFavoriteComicEntity())
        }
    }

    override suspend fun removeAll() = withContext(Dispatchers.IO) {
        runCatching { dao.removeAll() }
    }
}

private val ComicsListSorting.sql
    get() = when (sortBy) {
        ComicsSortOrder.Title -> "title"
        ComicsSortOrder.ReleaseDate -> "release_date"
    }.let { if (descending) it + "desc" else it }
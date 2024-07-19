package com.vallem.marvelhq.shared.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.vallem.marvelhq.shared.data.local.FavoriteComicsDao
import com.vallem.marvelhq.shared.data.local.entity.ComicEntity
import com.vallem.marvelhq.shared.data.mapper.toDomain
import com.vallem.marvelhq.shared.data.mapper.toEntity
import com.vallem.marvelhq.shared.domain.exception.ComicNotFavoritedError
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.repository.FavoriteComicsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomFavoriteComicsRepository(private val dao: FavoriteComicsDao) : FavoriteComicsRepository {
    override suspend fun save(comic: Comic) = dao.save(comic.toEntity())

    override fun retrievePages(pageSize: Int): Flow<PagingData<Comic>> = Pager(
        config = PagingConfig(pageSize),
        pagingSourceFactory = { dao.retrieveAll() },
    ).flow.map { it.map(ComicEntity::toDomain) }

    override suspend fun remove(comic: Comic) {
        if (dao.find(comic.id) == null) throw ComicNotFavoritedError(comic)
        dao.remove(comic.toEntity())
    }

    override suspend fun removeAll() = dao.removeAll()
}
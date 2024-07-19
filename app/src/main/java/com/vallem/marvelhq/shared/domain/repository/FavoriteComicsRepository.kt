package com.vallem.marvelhq.shared.domain.repository

import androidx.paging.PagingData
import com.vallem.marvelhq.shared.domain.exception.ComicNotFavoritedError
import com.vallem.marvelhq.shared.domain.model.Comic
import kotlinx.coroutines.flow.Flow

interface FavoriteComicsRepository {
    suspend fun save(comic: Comic)
    fun retrievePages(pageSize: Int): Flow<PagingData<Comic>>

    /**
     * @throws ComicNotFavoritedError
     */
    suspend fun remove(comic: Comic)
    suspend fun removeAll()
}
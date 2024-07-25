package com.vallem.marvelhq.shared.domain.repository

import com.vallem.marvelhq.shared.domain.exception.ComicNotFavoritedError
import com.vallem.marvelhq.shared.domain.model.Comic

interface FavoriteComicsRepository {
    suspend fun save(comic: Comic): Result<Unit>
    suspend fun retrieve(id: Int): Result<Comic?>
    suspend fun retrievePages(pageSize: Int): Result<List<Comic>>

    /**
     * @throws ComicNotFavoritedError
     */
    suspend fun remove(comic: Comic): Result<Unit>
    suspend fun removeAll(): Result<Unit>
}
package com.vallem.marvelhq.shared.domain.repository

import com.vallem.marvelhq.shared.domain.exception.ComicNotFavoritedError
import com.vallem.marvelhq.shared.domain.model.Comic

interface FavoriteComicsRepository {
    suspend fun save(comic: Comic)
    suspend fun retrieve(id: Int): Comic?
    fun retrievePages(pageSize: Int): List<Comic>

    /**
     * @throws ComicNotFavoritedError
     */
    suspend fun remove(comic: Comic)
    suspend fun removeAll()
}
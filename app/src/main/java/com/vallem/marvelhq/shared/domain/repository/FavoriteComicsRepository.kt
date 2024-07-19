package com.vallem.marvelhq.shared.domain.repository

import com.vallem.marvelhq.shared.domain.exception.ComicNotFavoritedError
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.model.PageResult

interface FavoriteComicsRepository {
    fun save(comic: Comic)
    fun retrieveNextPage(page: Int, size: Int): PageResult<Comic>

    /**
     * @throws ComicNotFavoritedError
     */
    fun remove(comic: Comic)
    fun removeAll()
}
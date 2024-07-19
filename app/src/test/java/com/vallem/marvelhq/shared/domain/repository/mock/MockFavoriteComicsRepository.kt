package com.vallem.marvelhq.shared.domain.repository.mock

import com.vallem.marvelhq.shared.domain.exception.ComicNotFavoritedError
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.model.PageResult
import com.vallem.marvelhq.shared.domain.repository.FavoriteComicsRepository
import com.vallem.marvelhq.shared.domain.repository.util.generate
import com.vallem.marvelhq.shared.domain.repository.util.randomComic

class MockFavoriteComicsRepository : FavoriteComicsRepository {
    private val favorites = generate(16) { randomComic() }.toMutableList()

    override fun save(comic: Comic) {
        favorites.add(comic)
    }

    override fun retrieveNextPage(page: Int, size: Int) =
        if (page == 0) PageResult.Success(favorites.take(size), 0, favorites.size)
        else PageResult.Failure(Throwable())

    override fun remove(comic: Comic) {
        val index = favorites.indexOfFirst { it.id == comic.id }
        if (index < 0) throw ComicNotFavoritedError(comic)

        favorites.removeAt(index)
    }

    override fun removeAll() = favorites.clear()
}

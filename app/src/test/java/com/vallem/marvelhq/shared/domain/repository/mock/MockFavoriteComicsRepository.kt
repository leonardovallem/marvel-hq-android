package com.vallem.marvelhq.shared.domain.repository.mock

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vallem.marvelhq.shared.domain.exception.ComicNotFavoritedError
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.repository.FavoriteComicsRepository
import com.vallem.marvelhq.shared.domain.repository.util.generate
import com.vallem.marvelhq.shared.domain.repository.util.randomComic
import kotlinx.coroutines.flow.Flow

class MockFavoriteComicsRepository : FavoriteComicsRepository {
    private val favorites = generate(16) { randomComic() }.toMutableList()

    override suspend fun save(comic: Comic) {
        favorites.add(comic)
    }

    override fun retrievePages(pageSize: Int): Flow<PagingData<Comic>> = Pager(
        config = PagingConfig(pageSize, initialLoadSize = pageSize),
        pagingSourceFactory = { MockPagingSource(favorites, pageSize) }
    ).flow

    override suspend fun remove(comic: Comic) {
        val index = favorites.indexOfFirst { it.id == comic.id }
        if (index < 0) throw ComicNotFavoritedError(comic)

        favorites.removeAt(index)
    }

    override suspend fun removeAll() = favorites.clear()
}

private class MockPagingSource(private val comics: List<Comic>, private val pageSize: Int) : PagingSource<Int, Comic>() {
    override val keyReuseSupported = true

    override fun getRefreshKey(state: PagingState<Int, Comic>) = null
    override suspend fun load(params: LoadParams<Int>): LoadResult.Page<Int, Comic> = LoadResult.Page(comics.take(pageSize), null, null)
}
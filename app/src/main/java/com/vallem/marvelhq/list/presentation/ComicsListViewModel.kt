package com.vallem.marvelhq.list.presentation

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vallem.marvelhq.shared.AppConstants.DefaultPageSize
import com.vallem.marvelhq.shared.domain.model.Comic
import kotlinx.coroutines.delay

class ComicsListViewModel : ViewModel() {
    companion object {
        fun mockComic() = Comic(
            id = 1689,
            title = "Official Handbook of the Marvel Universe (2004) #10 (MARVEL KNIGHTS)",
            thumbnailUrl = "http://i.annihil.us/u/prod/marvel/i/mg/9/30/4bc64df4105b9.jpg"
        )

        val comics = Pager(
            config = PagingConfig(DefaultPageSize, initialLoadSize = DefaultPageSize),
            pagingSourceFactory = {
                MockPagingSource(
                    List(100) { mockComic() }.toMutableStateList(),
                    DefaultPageSize
                )
            }
        ).flow
    }
}

private class MockPagingSource(
    private val comics: List<Comic>,
    private val pageSize: Int,
) : PagingSource<Int, Comic>() {
    override val keyReuseSupported = true

    override fun getRefreshKey(state: PagingState<Int, Comic>) = null
    override suspend fun load(params: LoadParams<Int>): LoadResult.Page<Int, Comic> = run {
        delay(5_000)
        LoadResult.Page(comics.take(pageSize), null, null)
    }
}
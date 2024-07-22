package com.vallem.marvelhq.shared.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vallem.marvelhq.shared.data.remote.dto.ComicDto
import com.vallem.marvelhq.shared.data.remote.dto.ComicsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ComicsPagingSource(private val httpClient: HttpClient) : PagingSource<Int, ComicDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ComicDto> {
        return try {
            val data = httpClient.get(
                ApiRoutes.comics(
                    limit = params.loadSize,
                    offset = params.key?.let { it * params.loadSize } ?: 0,
                )
            ).body<ComicsResponseDto>()

            LoadResult.Page(
                data = data.data.results,
                prevKey = params.key?.takeUnless { it == 0 },
                nextKey = params.key?.plus(1)?.takeUnless {
                    it * params.loadSize > data.data.total
                }
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ComicDto>) = state.anchorPosition
        ?.let(state::closestPageToPosition)
        ?.prevKey
}
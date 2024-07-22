package com.vallem.marvelhq.shared.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vallem.marvelhq.shared.AppConstants.DefaultPageSize
import com.vallem.marvelhq.shared.data.mapper.toDomain
import com.vallem.marvelhq.shared.data.remote.ComicsPagingSource
import com.vallem.marvelhq.shared.data.util.mapNotNull
import com.vallem.marvelhq.shared.domain.repository.ComicsRepository
import kotlinx.coroutines.flow.map

class RemoteComicsRepository(private val pagingSource: ComicsPagingSource) : ComicsRepository {
    override fun retrieveNextPage(pageSize: Int) = Pager(
        config = PagingConfig(pageSize = DefaultPageSize),
        pagingSourceFactory = { pagingSource }
    ).flow.map { pagingData ->
        pagingData.mapNotNull { it.toDomain() }
    }
}
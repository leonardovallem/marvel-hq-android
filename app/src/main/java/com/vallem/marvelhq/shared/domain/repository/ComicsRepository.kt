package com.vallem.marvelhq.shared.domain.repository

import androidx.paging.PagingData
import com.vallem.marvelhq.shared.domain.model.Comic
import kotlinx.coroutines.flow.Flow

interface ComicsRepository {
    fun retrieveNextPage(pageSize: Int): Flow<PagingData<Comic>>
}
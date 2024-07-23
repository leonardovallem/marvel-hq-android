package com.vallem.marvelhq.shared.domain.repository

import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.presentation.pagination.PaginationResult

interface ComicsRepository {
    suspend fun loadPage(page: Int, pageSize: Int, initialPage: Int): PaginationResult<Comic>
}
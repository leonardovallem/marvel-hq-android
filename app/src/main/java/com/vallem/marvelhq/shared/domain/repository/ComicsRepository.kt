package com.vallem.marvelhq.shared.domain.repository

import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.model.PageResult

interface ComicsRepository {
    fun retrieveNextPage(page: Int, size: Int): PageResult<Comic>
}
package com.vallem.marvelhq.shared.domain.repository.mock

import com.vallem.marvelhq.shared.domain.repository.ComicsRepository
import com.vallem.marvelhq.shared.domain.repository.util.generate
import com.vallem.marvelhq.shared.domain.repository.util.randomComic
import com.vallem.marvelhq.shared.presentation.pagination.PaginationResult

class MockComicsRepository : ComicsRepository {
    private val comics = generate(16) { randomComic() }

    override suspend fun loadPage(page: Int, pageSize: Int, initialPage: Int) =
        if (page == initialPage) PaginationResult.Success(comics.take(pageSize), true)
        else PaginationResult.Failure(Throwable(), true)
}

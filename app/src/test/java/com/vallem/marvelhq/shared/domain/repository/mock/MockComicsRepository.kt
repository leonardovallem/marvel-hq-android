package com.vallem.marvelhq.shared.domain.repository.mock

import com.vallem.marvelhq.shared.domain.model.PageResult
import com.vallem.marvelhq.shared.domain.repository.ComicsRepository
import com.vallem.marvelhq.shared.domain.repository.util.generate
import com.vallem.marvelhq.shared.domain.repository.util.randomComic

class MockComicsRepository : ComicsRepository {
    private val comics = generate(16) { randomComic() }

    override fun retrieveNextPage(page: Int, size: Int) =
        if (page == 0) PageResult.Success(
            data = comics.take(size),
            page = 0,
            total = comics.size
        )
        else PageResult.Failure(Throwable())
}

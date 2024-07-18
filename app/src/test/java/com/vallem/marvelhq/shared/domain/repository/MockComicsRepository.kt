package com.vallem.marvelhq.shared.domain.repository

import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.model.PageResult

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

private fun randomComic() = Comic("Comic")

private fun <T> generate(count: Int, builder: () -> T) = List(count) { builder() }
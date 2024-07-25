package com.vallem.marvelhq.shared.domain.repository

import com.vallem.marvelhq.shared.domain.repository.mock.MockFavoriteComicsRepository
import com.vallem.marvelhq.shared.domain.repository.util.randomComic
import com.vallem.marvelhq.shared.presentation.pagination.PaginationResult
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.test.fail

private const val PageSize = 15

class FavoriteComicsRepositoryTest : StringSpec({
    lateinit var repository: FavoriteComicsRepository

    beforeEach {
        repository = MockFavoriteComicsRepository()
    }

    "favoriting a valid comic should succeed" {
        shouldNotThrowAny {
            val comic = randomComic()
            repository.save(comic)
        }
    }

    "unfavoriting a favorited comic should succeed" {
        shouldNotThrowAny {
            val comic = randomComic()
            repository.save(comic)
            repository.remove(comic)
        }
    }

    "unfavoriting a not-favorited comic should fail" {
        repository.removeAll()

        val comic = randomComic()
        repository.remove(comic).isFailure shouldBe true
    }

    "should return a page of comics" {
        val page = repository.loadPage(1, 15, 1)

        if (page is PaginationResult.Success) page.data.size shouldBe PageSize
        else fail()
    }
})
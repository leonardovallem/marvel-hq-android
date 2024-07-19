package com.vallem.marvelhq.shared.domain.repository

import com.vallem.marvelhq.shared.domain.exception.ComicNotFavoritedError
import com.vallem.marvelhq.shared.domain.model.getOrNull
import com.vallem.marvelhq.shared.domain.model.isSuccess
import com.vallem.marvelhq.shared.domain.repository.mock.MockFavoriteComicsRepository
import com.vallem.marvelhq.shared.domain.repository.util.randomComic
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

private const val PageSize = 15

class FavoriteComicsRepositoryTest : StringSpec({
    lateinit var repository: FavoriteComicsRepository

    beforeAny {
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
            val comic = repository.retrieveNextPage(page = 0, size = 1)
                .getOrNull()!!
                .data
                .first()

            repository.remove(comic)
        }
    }

    "unfavoriting a not-favorited comic should fail" {
        shouldThrow<ComicNotFavoritedError> {
            repository.removeAll()

            val comic = randomComic()
            repository.remove(comic)
        }
    }


    "should return a page of comics for a valid page number" {
        val pageResult = repository.retrieveNextPage(page = 0, size = PageSize)
        pageResult.isSuccess shouldBe true

        pageResult.getOrNull() shouldNotBeNull {
            data.size shouldBe PageSize
            page shouldBe 0
        }
    }

    "should return a failure page result for an invalid page number" {
        val pageResult = repository.retrieveNextPage(page = -1, size = PageSize)

        pageResult.isSuccess shouldBe false
        pageResult.getOrNull() shouldBe null
    }
})
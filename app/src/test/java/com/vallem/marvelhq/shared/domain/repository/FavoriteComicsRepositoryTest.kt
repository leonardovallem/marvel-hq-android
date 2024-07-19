package com.vallem.marvelhq.shared.domain.repository

import androidx.paging.testing.asSnapshot
import com.vallem.marvelhq.shared.domain.exception.ComicNotFavoritedError
import com.vallem.marvelhq.shared.domain.repository.mock.MockFavoriteComicsRepository
import com.vallem.marvelhq.shared.domain.repository.util.randomComic
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

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
        shouldThrow<ComicNotFavoritedError> {
            repository.removeAll()

            val comic = randomComic()
            repository.remove(comic)
        }
    }

    "should return a page of comics" {
        val page = repository.retrievePages(15).asSnapshot()
        page.size shouldBe PageSize
    }
})
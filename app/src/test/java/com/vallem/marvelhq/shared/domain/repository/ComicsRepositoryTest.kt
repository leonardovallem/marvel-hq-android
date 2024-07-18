package com.vallem.marvelhq.shared.domain.repository

import com.vallem.marvelhq.shared.domain.model.getOrNull
import com.vallem.marvelhq.shared.domain.model.isSuccess
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

private const val PageSize = 15

class ComicsRepositoryTest : StringSpec({
    val repository: ComicsRepository = MockComicsRepository()

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
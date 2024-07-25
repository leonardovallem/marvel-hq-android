package com.vallem.marvelhq.shared.domain.repository

import com.vallem.marvelhq.shared.domain.repository.mock.MockComicsRepository
import com.vallem.marvelhq.shared.presentation.pagination.PaginationResult
import com.vallem.marvelhq.shared.presentation.pagination.isSuccess
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.test.fail

private const val PageSize = 15

class ComicsRepositoryTest : StringSpec({
    val repository: ComicsRepository = MockComicsRepository()

    "should return a page of comics for a valid page number" {
        val pageResult = repository.loadPage(page = 1, PageSize, 1)

        if (pageResult is PaginationResult.Success) pageResult.data.size shouldBe PageSize
        else fail()
    }

    "should return a failure page result for an invalid page number" {
        val pageResult = repository.loadPage(page = -1, PageSize, 1)
        pageResult.isSuccess shouldBe false
    }
})
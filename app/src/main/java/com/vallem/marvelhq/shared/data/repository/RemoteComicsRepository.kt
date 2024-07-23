package com.vallem.marvelhq.shared.data.repository

import com.vallem.marvelhq.shared.data.mapper.toDomain
import com.vallem.marvelhq.shared.data.remote.ApiRoutes
import com.vallem.marvelhq.shared.data.remote.dto.ComicDto
import com.vallem.marvelhq.shared.data.remote.dto.ComicsResponseDto
import com.vallem.marvelhq.shared.domain.repository.ComicsRepository
import com.vallem.marvelhq.shared.presentation.pagination.PaginationResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemoteComicsRepository(private val httpClient: HttpClient) : ComicsRepository {
    override suspend fun loadPage(
        page: Int,
        pageSize: Int,
        initialPage: Int
    ) = try {
        httpClient.get(ApiRoutes.comics(pageSize, offset = page * pageSize))
            .body<ComicsResponseDto>()
            .data
            .results
            .mapNotNull(ComicDto::toDomain)
            .let { PaginationResult.Success(it, page == initialPage) }
    } catch (t: Throwable) {
        PaginationResult.Failure(t, page == initialPage)
    }
}
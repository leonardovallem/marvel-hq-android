package com.vallem.marvelhq.shared.data.repository

import com.vallem.marvelhq.shared.data.mapper.toDomain
import com.vallem.marvelhq.shared.data.remote.ApiRoutes
import com.vallem.marvelhq.shared.data.remote.dto.ComicsResponseDto
import com.vallem.marvelhq.shared.domain.repository.ComicsRepository
import com.vallem.marvelhq.shared.domain.repository.FavoriteComicsRepository
import com.vallem.marvelhq.shared.presentation.pagination.PaginationResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemoteComicsRepository(
    private val httpClient: HttpClient,
    private val favoriteComicsRepository: FavoriteComicsRepository,
) : ComicsRepository {
    override suspend fun loadPage(
        page: Int,
        pageSize: Int,
        initialPage: Int
    ) = try {
        val comics = httpClient.get(ApiRoutes.comics(pageSize, offset = page * pageSize))
            .body<ComicsResponseDto>()
            .data
            .results

        comics.mapNotNull {
            val comic = favoriteComicsRepository.retrieve(it.id ?: return@mapNotNull null)
            it.toDomain(comic?.isFavorite == true)
        }.let {
            PaginationResult.Success(it, page == initialPage)
        }
    } catch (t: Throwable) {
        PaginationResult.Failure(t, page == initialPage)
    }
}
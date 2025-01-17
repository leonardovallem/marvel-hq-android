package com.vallem.marvelhq.shared.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ComicsResponseDto(val data: ComicsPageDto)

@Serializable
data class ComicsPageDto(val results: List<ComicDto>, val count: Int, val total: Int)

@Serializable
data class ComicDto(
    val id: Int?,
    val title: String?,
    val thumbnail: ComicThumbnail?,
    val description: String?,
    val dates: List<ComicDates>?
)

@Serializable
data class ComicThumbnail(val path: String, val extension: String)

@Serializable
data class ComicDates(val type: String, val date: String)
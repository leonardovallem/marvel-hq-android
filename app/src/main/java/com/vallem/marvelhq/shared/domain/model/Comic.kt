package com.vallem.marvelhq.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Comic(
    val id: Int,
    val title: String,
    val description: String?,
    val thumbnailUrl: String?,
    val releaseDate: String?,
)

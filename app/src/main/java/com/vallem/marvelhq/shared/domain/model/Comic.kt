package com.vallem.marvelhq.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Comic(
    val id: Int,
    val title: String,
    val thumbnailUrl: String?,
)

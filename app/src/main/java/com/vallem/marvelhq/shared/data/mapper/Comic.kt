package com.vallem.marvelhq.shared.data.mapper

import com.vallem.marvelhq.shared.data.local.entity.FavoriteComicEntity
import com.vallem.marvelhq.shared.data.remote.dto.ComicDto
import com.vallem.marvelhq.shared.domain.model.Comic

fun FavoriteComicEntity.toDomain() = run {
    Comic(id, title, description, thumbUrl, releaseDate)
}

fun Comic.toFavoriteComicEntity() = run {
    FavoriteComicEntity(id, title, description, thumbnailUrl, releaseDate)
}

fun ComicDto.toDomain() = run {
    Comic(
        id = id ?: return@run null,
        title = title ?: return@run null,
        description = description?.takeUnless { it.isBlank() },
        thumbnailUrl = thumbnail?.run { "$path.$extension" },
        releaseDate = dates?.find { it.type == "focDate" }?.date
    )
}
package com.vallem.marvelhq.shared.data.mapper

import com.vallem.marvelhq.shared.data.local.entity.ComicEntity
import com.vallem.marvelhq.shared.data.remote.dto.ComicDto
import com.vallem.marvelhq.shared.domain.model.Comic

fun ComicEntity.toDomain() = run {
    Comic(id, title, description, thumbUrl)
}

fun Comic.toEntity() = run {
    ComicEntity(id, title, description, thumbnailUrl)
}

fun ComicDto.toDomain() = run {
    Comic(
        id = id ?: return@run null,
        title = title ?: return@run null,
        description = description,
        thumbnailUrl = thumbnail?.run { "$path.$extension" },
    )
}
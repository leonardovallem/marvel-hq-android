package com.vallem.marvelhq.shared.data.mapper

import com.vallem.marvelhq.shared.data.local.entity.ComicEntity
import com.vallem.marvelhq.shared.domain.model.Comic

fun ComicEntity.toDomain() = run {
    Comic(id, title)
}

fun Comic.toEntity() = run {
    ComicEntity(id, title)
}
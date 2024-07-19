package com.vallem.marvelhq.shared.domain.exception

import com.vallem.marvelhq.shared.domain.model.Comic

data class ComicNotFavoritedError(
    val comic: Comic
) : IllegalArgumentException("Comic with id ${comic.id} is not favorited")
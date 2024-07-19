package com.vallem.marvelhq.shared.domain.repository.util

import com.vallem.marvelhq.shared.domain.model.Comic
import kotlin.random.Random

fun randomComic() = Comic(Random.nextInt(), "Comic")

fun <T> generate(count: Int, builder: () -> T) = List(count) { builder() }
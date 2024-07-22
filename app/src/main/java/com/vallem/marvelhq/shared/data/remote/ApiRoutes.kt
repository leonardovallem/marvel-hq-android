package com.vallem.marvelhq.shared.data.remote

object ApiRoutes {
    const val BaseUrl = "gateway.marvel.com/v1/public"
    fun comics(limit: Int, offset: Int) = "/comics?format=comics&limit=$limit&offset=$offset"
}
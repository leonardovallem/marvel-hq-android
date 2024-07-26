package com.vallem.marvelhq.shared.data.remote

import com.vallem.marvelhq.list.presentation.model.ComicsListFilters
import com.vallem.marvelhq.list.presentation.model.queryParams

object ApiRoutes {
    const val BaseUrl = "gateway.marvel.com/v1/public"
    fun comics(filters: ComicsListFilters, limit: Int, offset: Int) =
        "/comics?format=comic&formatType=comic&${filters.queryParams}&limit=$limit&offset=$offset"
}
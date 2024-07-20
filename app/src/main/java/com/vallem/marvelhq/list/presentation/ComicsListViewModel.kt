package com.vallem.marvelhq.list.presentation

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.vallem.marvelhq.shared.domain.model.Comic

class ComicsListViewModel : ViewModel() {
    companion object {
        fun mockComic() = Comic(
            id = 1689,
            title = "Official Handbook of the Marvel Universe (2004) #10 (MARVEL KNIGHTS)",
            thumbnailUrl = "http://i.annihil.us/u/prod/marvel/i/mg/9/30/4bc64df4105b9.jpg"
        )

        val comics = List(100) { mockComic() }.toMutableStateList()
    }
}
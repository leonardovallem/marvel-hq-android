package com.vallem.marvelhq.details.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vallem.marvelhq.details.presentation.model.FavState
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.repository.FavoriteComicsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComicDetailsViewModel(
    initialComic: Comic,
    private val favoriteComicsRepository: FavoriteComicsRepository,
) : ViewModel() {
    var comic by mutableStateOf(initialComic)
        private set

    var favState by mutableStateOf(FavState.Loading)
        private set

    init {
        viewModelScope.launch {
            val favorited = favoriteComicsRepository.retrieve(comic.id).getOrNull()
            favState = if (favorited == null) FavState.NotFavorited else FavState.Favorited
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (favState == FavState.Favorited) {
                    favState = FavState.Loading

                    favoriteComicsRepository.remove(comic)
                        .onSuccess { favState = FavState.NotFavorited }
                        .onFailure { favState = FavState.Favorited }
                } else {
                    favState = FavState.Loading

                    favoriteComicsRepository.save(comic)
                        .onSuccess { favState = FavState.Favorited }
                        .onFailure { favState = FavState.NotFavorited }
                }
            }
        }
    }
}
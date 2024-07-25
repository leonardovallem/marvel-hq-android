package com.vallem.marvelhq.favorites.di

import com.vallem.marvelhq.favorites.presentation.FavoriteComicsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val FavoriteComicsModule = module {
    viewModel { FavoriteComicsViewModel(get()) }
}
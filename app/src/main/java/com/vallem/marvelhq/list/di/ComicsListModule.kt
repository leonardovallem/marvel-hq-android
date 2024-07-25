package com.vallem.marvelhq.list.di

import com.vallem.marvelhq.list.presentation.ComicsListViewModel
import com.vallem.marvelhq.list.presentation.FavoriteComicsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ComicsListModule = module {
    viewModel { ComicsListViewModel(get()) }
    viewModel { FavoriteComicsViewModel(get()) }
}
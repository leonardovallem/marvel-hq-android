package com.vallem.marvelhq.details.di

import com.vallem.marvelhq.details.presentation.ComicDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ComicDetailsModule = module {
    viewModel { params -> ComicDetailsViewModel(params.get(), get()) }
}
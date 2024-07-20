package com.vallem.marvelhq.list.di

import com.vallem.marvelhq.list.presentation.ComicsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ComicsListModule = module {
    viewModel { ComicsListViewModel() }
}
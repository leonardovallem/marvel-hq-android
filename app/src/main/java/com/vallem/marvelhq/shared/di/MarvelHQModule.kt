package com.vallem.marvelhq.shared.di

import androidx.room.Room
import com.vallem.marvelhq.list.di.ComicsListModule
import com.vallem.marvelhq.shared.data.local.FavoriteComicsDao
import com.vallem.marvelhq.shared.data.local.MarvelHQDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val MarvelHQModule = module {
    includes(ComicsListModule)

    single<MarvelHQDatabase> {
        Room.databaseBuilder(androidContext(), MarvelHQDatabase::class.java, "marvel_hq_db")
            .build()
    }

    factory<FavoriteComicsDao> { get<MarvelHQDatabase>().favoriteComicsDao }
}
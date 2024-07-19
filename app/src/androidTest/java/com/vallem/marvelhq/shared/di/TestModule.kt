package com.vallem.marvelhq.shared.di

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.vallem.marvelhq.shared.data.local.FavoriteComicsDao
import com.vallem.marvelhq.shared.data.local.MarvelHQDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val TestModule = module {
    single<MarvelHQDatabase> {
        Room.inMemoryDatabaseBuilder(androidContext(), MarvelHQDatabase::class.java).build()
    }

    factory<FavoriteComicsDao> { get<MarvelHQDatabase>().favoriteComicsDao }

    factory<Context> { InstrumentationRegistry.getInstrumentation().context }
}
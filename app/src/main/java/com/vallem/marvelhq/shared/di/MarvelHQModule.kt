package com.vallem.marvelhq.shared.di

import androidx.room.Room
import com.vallem.marvelhq.BuildConfig
import com.vallem.marvelhq.list.di.ComicsListModule
import com.vallem.marvelhq.shared.AppConstants.MarvelBaseUrl
import com.vallem.marvelhq.shared.data.local.FavoriteComicsDao
import com.vallem.marvelhq.shared.data.local.MarvelHQDatabase
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.bearerAuth
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val MarvelHQModule = module {
    includes(ComicsListModule)

    single<MarvelHQDatabase> {
        Room.databaseBuilder(androidContext(), MarvelHQDatabase::class.java, "marvel_hq_db")
            .build()
    }

    factory<FavoriteComicsDao> { get<MarvelHQDatabase>().favoriteComicsDao }

    single<HttpClient> {
        HttpClient(OkHttp) {
            install(Resources)

            defaultRequest {
                url(MarvelBaseUrl)
                bearerAuth(BuildConfig.MARVEL_API_PUBLIC_KEY)
            }
        }
    }
}
package com.vallem.marvelhq.shared.di

import androidx.room.Room
import com.vallem.marvelhq.list.di.ComicsListModule
import com.vallem.marvelhq.shared.data.local.FavoriteComicsDao
import com.vallem.marvelhq.shared.data.local.MarvelHQDatabase
import com.vallem.marvelhq.shared.data.remote.ApiRoutes
import com.vallem.marvelhq.shared.data.repository.RemoteComicsRepository
import com.vallem.marvelhq.shared.data.repository.RoomFavoriteComicsRepository
import com.vallem.marvelhq.shared.data.util.MarvelApiAuthData
import com.vallem.marvelhq.shared.domain.repository.ComicsRepository
import com.vallem.marvelhq.shared.domain.repository.FavoriteComicsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
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
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = ApiRoutes.BaseUrl
                    parameters.run {
                        val apiAuthData = MarvelApiAuthData.generate()
                        append("apikey", apiAuthData.apiKey)
                        append("ts", apiAuthData.timestamp)
                        append("hash", apiAuthData.hash)
                    }
                }
            }
        }
    }

    factory<ComicsRepository> { RemoteComicsRepository(get(), get()) }
    factory<FavoriteComicsRepository> { RoomFavoriteComicsRepository(get()) }
}
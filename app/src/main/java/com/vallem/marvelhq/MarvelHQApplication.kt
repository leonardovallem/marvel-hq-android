package com.vallem.marvelhq

import android.app.Application
import com.vallem.marvelhq.shared.di.MarvelHQModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarvelHQApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MarvelHQApplication)
            modules(MarvelHQModule)
        }
    }
}
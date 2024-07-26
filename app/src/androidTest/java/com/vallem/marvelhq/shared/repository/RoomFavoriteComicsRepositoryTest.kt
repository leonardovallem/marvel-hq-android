package com.vallem.marvelhq.shared.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.vallem.marvelhq.shared.data.repository.RoomFavoriteComicsRepository
import com.vallem.marvelhq.shared.di.TestModule
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.domain.repository.FavoriteComicsRepository
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.junit5.KoinTestExtension
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class RoomFavoriteComicsRepositoryTest : KoinTest {
    private val repository: FavoriteComicsRepository = RoomFavoriteComicsRepository(get())

    @JvmField
    @RegisterExtension
    val diExtension = KoinTestExtension.create {
        androidContext(InstrumentationRegistry.getInstrumentation().context)
        modules(TestModule)
    }

    @Test
    fun favoritingValidComicShouldSucceed() = runTest {
        shouldNotThrowAny {
            val comic = randomComic()
            repository.save(comic)
        }
    }

    @Test
    fun unfavoritingFavoritedComicShouldSucceed() = runTest {
        shouldNotThrowAny {
            val comic = randomComic()
            repository.save(comic)
            repository.remove(comic)
        }
    }

    @Test
    fun unfavoritingNotFavoritedComicShouldFail() = runTest {
        repository.removeAll()

        val comic = randomComic()
        repository.remove(comic).isFailure shouldBe true
    }
}

fun randomComic() = Comic(Random.nextInt(), "Comic", null, null, null)

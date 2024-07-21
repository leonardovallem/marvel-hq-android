package com.vallem.marvelhq.list.presentation

import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.espresso.device.filter.RequiresDisplay
import androidx.test.espresso.device.sizeclass.HeightSizeClass.Companion.HeightSizeClassEnum
import androidx.test.espresso.device.sizeclass.WidthSizeClass.Companion.WidthSizeClassEnum
import com.vallem.marvelhq.shared.domain.model.Comic
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import org.junit.Rule
import org.junit.Test

class ComicsListScreenTest {
    private companion object {
        fun mockComic() = Comic(
            id = 1689,
            title = "Official Handbook of the Marvel Universe (2004) #10 (MARVEL KNIGHTS)",
            thumbnailUrl = "http://i.annihil.us/u/prod/marvel/i/mg/9/30/4bc64df4105b9.jpg"
        )

        val comics = List(100) { mockComic() }.toMutableStateList()
    }

    @get:Rule val composeRule = createComposeRule()

    @Test
    @RequiresDisplay(WidthSizeClassEnum.COMPACT, HeightSizeClassEnum.COMPACT)
    fun ensureLessComicsPerRowOnSmallerScreens(): Unit = composeRule.run {
        setContent {
            ComicsListScreenContent(comics)
        }

        val itemsPerRow = onNodeWithTag("comicsGrid")
            .fetchSemanticsNode()
            .config[ItemsPerRowSemanticProperty]
            .toInt()

        itemsPerRow shouldBeLessThanOrEqual 2
    }

    @Test
    @RequiresDisplay(WidthSizeClassEnum.MEDIUM, HeightSizeClassEnum.EXPANDED)
    fun ensureMoreComicsPerRowOnBiggerScreens(): Unit = composeRule.run {
        setContent {
            ComicsListScreenContent(comics)
        }

        val itemsPerRow = onNodeWithTag("comicsGrid")
            .fetchSemanticsNode()
            .config[ItemsPerRowSemanticProperty]
            .toInt()

        itemsPerRow shouldBeGreaterThan 2
    }
}
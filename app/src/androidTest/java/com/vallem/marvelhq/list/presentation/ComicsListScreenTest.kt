package com.vallem.marvelhq.list.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.espresso.device.filter.RequiresDisplay
import androidx.test.espresso.device.sizeclass.HeightSizeClass.Companion.HeightSizeClassEnum
import androidx.test.espresso.device.sizeclass.WidthSizeClass.Companion.WidthSizeClassEnum
import com.vallem.marvelhq.list.presentation.model.ComicsListFilters
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.presentation.component.ComicsListScreenContent
import com.vallem.marvelhq.shared.presentation.component.ItemsPerRowSemanticProperty
import com.vallem.marvelhq.shared.presentation.pagination.PaginationState
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalSharedTransitionApi::class)
class ComicsListScreenTest {
    private companion object {
        fun mockComic() = Comic(
            id = 1689,
            title = "Official Handbook of the Marvel Universe (2004) #10 (MARVEL KNIGHTS)",
            thumbnailUrl = "http://i.annihil.us/u/prod/marvel/i/mg/9/30/4bc64df4105b9.jpg",
            description = null,
            releaseDate = null,
        )

        val comics = List(100) { mockComic() }.toMutableStateList()
    }

    @get:Rule val composeRule = createComposeRule()

    @Test
    @RequiresDisplay(WidthSizeClassEnum.COMPACT, HeightSizeClassEnum.COMPACT)
    fun ensureLessComicsPerRowOnSmallerScreens(): Unit = composeRule.run {
        setContent {
            SharedTransitionLayout {
                AnimatedContent(true) {
                    if (it) ComicsListScreenContent(
                        comics = comics,
                        filters = ComicsListFilters(),
                        appendState = PaginationState.Append.NotLoading,
                        refreshState = PaginationState.Refresh.NotLoading,
                        retryPagination = {},
                        onFiltersChange = {},
                        loadNextPage = {},
                        onComicClick = {},
                        animatedContentScope = this,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                }
            }
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
            SharedTransitionLayout {
                AnimatedContent(true) {
                    if (it) ComicsListScreenContent(
                        comics = comics,
                        filters = ComicsListFilters(),
                        appendState = PaginationState.Append.NotLoading,
                        refreshState = PaginationState.Refresh.NotLoading,
                        retryPagination = {},
                        onFiltersChange = {},
                        loadNextPage = {},
                        onComicClick = {},
                        animatedContentScope = this,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                }
            }
        }

        val itemsPerRow = onNodeWithTag("comicsGrid")
            .fetchSemanticsNode()
            .config[ItemsPerRowSemanticProperty]
            .toInt()

        itemsPerRow shouldBeGreaterThan 2
    }
}
package com.vallem.marvelhq.list.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.presentation.component.shimmeringBrush
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

object ComicCard {
    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    operator fun invoke(
        comic: Comic,
        onClick: () -> Unit,
        animatedContentScope: AnimatedContentScope,
        sharedTransitionScope: SharedTransitionScope,
        modifier: Modifier = Modifier,
    ) {
        Surface(
            color = Color.Transparent,
            onClick = onClick,
            modifier = modifier
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                ComicThumbnail(
                    url = comic.thumbnailUrl,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .fillMaxSize()
                        .run {
                            sharedTransitionScope.run {
                                sharedElement(
                                    state = rememberSharedContentState(ThumbnailElementKey + comic.id),
                                    animatedVisibilityScope = animatedContentScope,
                                )
                            }
                        }
                )

                Text(
                    text = comic.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = sharedTransitionScope.run {
                        Modifier.sharedElement(
                            state = rememberSharedContentState(TitleElementKey + comic.id),
                            animatedVisibilityScope = animatedContentScope,
                        )
                    }
                )
            }
        }
    }

    @Composable
    fun Skeleton(modifier: Modifier = Modifier) {
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(
                modifier = Modifier
                    .background(shimmeringBrush(), MaterialTheme.shapes.small)
                    .aspectRatio(2 / 3f)
            )

            Spacer(modifier = Modifier.height(2.dp))

            repeat(3) {
                Box(
                    modifier = Modifier
                        .background(shimmeringBrush(), MaterialTheme.shapes.large)
                        .height(20.dp)
                        .fillMaxWidth()
                )
            }
        }
    }

    const val ThumbnailElementKey = "COMIC_CARD_THUMBNAIL"
    const val TitleElementKey = "COMIC_CARD_TITLE"
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun ComicCardPreview() {
    MarvelHQTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp)
        ) {
            SharedTransitionLayout {
                AnimatedContent(true) {
                    if (it) ComicCard(
                        comic = remember { Comic(0, "Comic title", null, null) },
                        onClick = {},
                        animatedContentScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ComicCardSkeletonPreview() {
    MarvelHQTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp)
        ) {
            ComicCard.Skeleton()
        }
    }
}
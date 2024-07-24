package com.vallem.marvelhq.details.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import coil.size.Size
import com.vallem.marvelhq.list.presentation.component.ComicCard.ThumbnailElementKey
import com.vallem.marvelhq.list.presentation.component.ComicCard.TitleElementKey
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.presentation.component.ShimmeringSkeleton
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ComicDetailsScreen(
    comic: Comic,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Scaffold(modifier = modifier) { pv ->
        Column(modifier = Modifier.padding(pv)) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(comic.thumbnailUrl)
                    .crossfade(true)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
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
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> ShimmeringSkeleton(
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(2 / 3f)
                    )

                    is AsyncImagePainter.State.Error -> Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .fillMaxSize()
                    )

                    else -> SubcomposeAsyncImageContent()
                }
            }

            Text(
                text = comic.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.run {
                    sharedTransitionScope.run {
                        sharedElement(
                            state = rememberSharedContentState(TitleElementKey + comic.id),
                            animatedVisibilityScope = animatedContentScope,
                        )
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun ComicDetailsScreenPreview() {
    MarvelHQTheme {
        SharedTransitionLayout {
            AnimatedContent(true, label = "") {
                if (it) ComicDetailsScreen(
                    comic = remember { Comic(0, "Marvel comic", "") },
                    animatedContentScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout,
                )
            }
        }
    }
}
package com.vallem.marvelhq.list.presentation.component

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import coil.size.Size
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.presentation.component.ShimmeringSkeleton
import com.vallem.marvelhq.shared.presentation.component.shimmeringBrush
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

object ComicCard {
    @Composable
    operator fun invoke(comic: Comic, modifier: Modifier = Modifier) {
        val context = LocalContext.current

        Surface(
            color = Color.Transparent,
            modifier = modifier
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
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
}

@Preview
@Composable
private fun ComicCardPreview() {
    MarvelHQTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp)
        ) {
            ComicCard(comic = remember { Comic(0, "Comic title", null) })
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
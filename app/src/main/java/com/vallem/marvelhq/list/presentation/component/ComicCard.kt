package com.vallem.marvelhq.list.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.shared.presentation.component.ShimmeringSkeleton
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

@Composable
fun ComicCard(comic: Comic, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Surface(
        color = Color.Transparent,
        modifier = modifier
            .widthIn(min = 128.dp)
            .heightIn(max = 360.dp)
    ) {
        Column {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(comic.thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .weight(1f)
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> ShimmeringSkeleton()

                    is AsyncImagePainter.State.Error -> Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                    )

                    else -> SubcomposeAsyncImageContent(contentScale = ContentScale.FillWidth)
                }
            }

            Text(
                text = comic.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
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
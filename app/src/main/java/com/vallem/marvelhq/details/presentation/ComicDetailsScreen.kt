package com.vallem.marvelhq.details.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vallem.marvelhq.list.presentation.component.ComicCard.ThumbnailElementKey
import com.vallem.marvelhq.list.presentation.component.ComicCard.TitleElementKey
import com.vallem.marvelhq.list.presentation.component.ComicThumbnail
import com.vallem.marvelhq.shared.domain.model.Comic
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ComicDetailsScreen(
    comic: Comic,
    navController: NavHostController,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) { _ ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                FilledIconButton(
                    onClick = navController::navigateUp,
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .5f),
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Voltar",
                    )
                }

                ComicThumbnail(
                    url = comic.thumbnailUrl,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .fillMaxWidth(.75f)
                        .run {
                            sharedTransitionScope.run {
                                sharedElement(
                                    state = rememberSharedContentState(ThumbnailElementKey + comic.id),
                                    animatedVisibilityScope = animatedContentScope,
                                )
                            }
                        }
                )

                FilledIconButton(
                    onClick = {},
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .5f),
                    ),
                ) {
                    AnimatedContent(targetState = comic.isFavorite) {
                        if (it) Icon(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = "Favoritar",
                            tint = MaterialTheme.colorScheme.error
                        )
                        else Icon(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = "Desfavoritar",
                            tint = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
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

            if (comic.description == null) Text(
                text = "Sem descrição",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        MaterialTheme.shapes.medium,
                    )
                    .padding(8.dp)
            )
            else Column(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        MaterialTheme.shapes.medium,
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = "Descrição",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = comic.description,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
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
                    comic = remember {
                        Comic(
                            0,
                            "Marvel comic",
                            "Definitely a Marvel comic",
                            "http://i.annihil.us/u/prod/marvel/i/mg/9/30/4bc64df4105b9.jpg",
                            true
                        )
                    },
                    animatedContentScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    navController = rememberNavController()
                )
            }
        }
    }
}
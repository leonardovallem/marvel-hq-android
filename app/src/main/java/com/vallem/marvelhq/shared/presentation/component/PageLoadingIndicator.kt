package com.vallem.marvelhq.shared.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vallem.marvelhq.R
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

@Composable
fun PageLoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Box {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.marvel_logo),
                contentDescription = "Carregando elementos",
                modifier = Modifier.height(32.dp)
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(shimmeringBrush(duration = 2000))
            )
        }
    }
}

@Preview
@Composable
private fun PageLoadingIndicatorPreview() {
    MarvelHQTheme {
        PageLoadingIndicator()
    }
}
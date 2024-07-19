package com.vallem.marvelhq.list.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vallem.marvelhq.ui.theme.MarvelHQTheme
import kotlinx.serialization.Serializable

@Serializable
object ComicsListScreen

@Composable
fun ComicsListScreen() {
    Scaffold { pv ->
        Column(modifier = Modifier.padding(pv)) {

        }
    }
}

@Preview
@Composable
private fun ComicsListScreenPreview() {
    MarvelHQTheme {
        ComicsListScreen()
    }
}
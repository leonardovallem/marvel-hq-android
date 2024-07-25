package com.vallem.marvelhq.list.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vallem.marvelhq.list.presentation.model.ComicsListFilters
import com.vallem.marvelhq.list.presentation.model.ComicsListOrder
import com.vallem.marvelhq.shared.presentation.component.ShimmeringSkeleton
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicsListFilters(filters: ComicsListFilters, modifier: Modifier = Modifier) {
    var bottomSheetShown by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf(filters.title) }

    if (bottomSheetShown) ModalBottomSheet(onDismissRequest = { bottomSheetShown = false }) {

    }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text(text = "Título") },
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.Search, contentDescription = "Buscar HQs pelo título")
        },
        trailingIcon = {
            FilledIconButton(
                onClick = { bottomSheetShown = true },
                modifier = Modifier.padding(end = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    contentDescription = "Abrir filtros de listagem",
                )
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            cursorColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    )
}

object ComicsListFilters {
    @Composable
    fun Skeleton(modifier: Modifier = Modifier) {
        ShimmeringSkeleton(
            modifier = modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .height(64.dp)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun ComicsListFiltersPreview() {
    MarvelHQTheme {
        ComicsListFilters(ComicsListFilters("", ComicsListOrder.ReleaseDate(true)))
    }
}

@Preview
@Composable
private fun ComicsListFiltersSkeletonPreview() {
    MarvelHQTheme {
        com.vallem.marvelhq.list.presentation.component.ComicsListFilters.Skeleton()
    }
}
package com.vallem.marvelhq.list.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vallem.marvelhq.list.presentation.model.ComicsListFilters
import com.vallem.marvelhq.shared.presentation.component.ShimmeringSkeleton
import com.vallem.marvelhq.ui.theme.MarvelHQTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicsFilters(
    filters: ComicsListFilters,
    onChange: (ComicsListFilters) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var bottomSheetShown by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf(filters.title) }
    var sortOrder by remember { mutableStateOf(filters.order) }

    if (bottomSheetShown) ModalBottomSheet(
        onDismissRequest = { bottomSheetShown = false },
        sheetState = sheetState,
        shape = MaterialTheme.shapes.medium.copy(
            bottomStart = CornerSize(0),
            bottomEnd = CornerSize(0),
        ),
    ) {
        ComicSortOrderSelectionSheetContent(
            sortOrder = sortOrder,
            onChange = {
                scope.launch {
                    sortOrder = it
                    onChange(filters.copy(order = it))
                    sheetState.hide()
                    bottomSheetShown = false
                }
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        )
    }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text(text = "Título") },
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.Search, contentDescription = "Buscar HQs pelo título")
        },
        trailingIcon = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnimatedVisibility(
                    visible = searchQuery.isNotBlank() || filters.title.isNotBlank(),
                    enter = fadeIn() + slideInHorizontally { it },
                    exit = slideOutHorizontally { it } + fadeOut(),
                ) {
                    IconButton(onClick = {
                        searchQuery = ""
                        if (filters.title.isNotBlank()) onChange(filters.copy(title = ""))
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = "Limpar pesquisa",
                        )
                    }
                }

                FilledIconButton(
                    onClick = {
                        scope.launch {
                            bottomSheetShown = true
                            sheetState.show()
                        }
                    },
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FilterList,
                        contentDescription = "Abrir filtros de listagem",
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            cursorColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onChange(filters.copy(title = searchQuery)) }),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
    )
}

object ComicsFilters {
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
        ComicsFilters(ComicsListFilters(), {})
    }
}

@Preview
@Composable
private fun ComicsListFiltersSkeletonPreview() {
    MarvelHQTheme {
        ComicsFilters.Skeleton()
    }
}
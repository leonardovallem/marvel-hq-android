package com.vallem.marvelhq.list.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vallem.marvelhq.list.presentation.model.ComicsListSorting
import com.vallem.marvelhq.list.presentation.model.ComicsSortOrder
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicSortOrderSelectionSheetContent(
    sortOrder: ComicsListSorting,
    onChange: (ComicsListSorting) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentOrder by remember { mutableStateOf(sortOrder) }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        Text(
            text = "Ordenar por",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            ComicsSortOrder.entries.forEach {
                ComicsSortOrderChip(
                    sortOrder = it,
                    onClick = { currentOrder = currentOrder.copy(sortBy = it) },
                    selected = currentOrder.sortBy == it,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            SingleChoiceSegmentedButtonRow {
                SegmentedButton(
                    selected = !currentOrder.descending,
                    onClick = { currentOrder = currentOrder.copy(descending = false) },
                    shape = MaterialTheme.shapes.medium.copy(
                        topEnd = CornerSize(0),
                        bottomEnd = CornerSize(0),
                    ),
                ) {
                    Text(text = "Crescente")
                }

                SegmentedButton(
                    selected = currentOrder.descending,
                    onClick = { currentOrder = currentOrder.copy(descending = true) },
                    shape = MaterialTheme.shapes.medium.copy(
                        topStart = CornerSize(0),
                        bottomStart = CornerSize(0),
                    ),
                ) {
                    Text(text = "Decrescente")
                }
            }
        }

        ElevatedButton(
            onClick = { onChange(currentOrder) },
            enabled = currentOrder != sortOrder,
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "ORDENAR")
        }
    }
}

@Preview
@Composable
private fun ComicSortOrderSelectionSheetContentPreview() {
    MarvelHQTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp),
        ) {
            ComicSortOrderSelectionSheetContent(ComicsListSorting(), {})
        }
    }
}
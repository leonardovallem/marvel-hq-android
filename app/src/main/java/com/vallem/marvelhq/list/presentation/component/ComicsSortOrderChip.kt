package com.vallem.marvelhq.list.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vallem.marvelhq.list.presentation.model.ComicsSortOrder
import com.vallem.marvelhq.list.presentation.model.icon
import com.vallem.marvelhq.list.presentation.model.label
import com.vallem.marvelhq.ui.theme.MarvelHQTheme

@Composable
fun ComicsSortOrderChip(
    sortOrder: ComicsSortOrder,
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val borderColor by animateColorAsState(targetValue = with(MaterialTheme.colorScheme) {
        if (selected) onSurface else tertiaryContainer
    })

    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        selected = selected,
        color = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        border = BorderStroke(2.dp, borderColor),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(imageVector = sortOrder.icon, contentDescription = null)

            Text(
                text = sortOrder.label,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun ComicsSortOrderChipPreview() {
    MarvelHQTheme {
        ComicsSortOrderChip(ComicsSortOrder.Title, {}, true)
    }
}
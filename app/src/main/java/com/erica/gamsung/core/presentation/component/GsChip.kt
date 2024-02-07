package com.erica.gamsung.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

private val DarkSlateGray = Color(0xFF334155)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GsChip(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit = {},
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = text,
                fontWeight = FontWeight.ExtraBold,
            )
        },
        colors =
            FilterChipDefaults.filterChipColors(
                labelColor = Color.Black,
                selectedContainerColor = DarkSlateGray,
                selectedLabelColor = Color.White,
            ),
        shape = CircleShape,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun ChipPreview() {
    Column(
        modifier = Modifier.background(Color.White),
    ) {
        Row {
            GsChip(text = "식당", selected = true)
            GsChip(text = "카페", selected = false)
        }
        Row {
            GsChip(text = "일", selected = false)
            GsChip(text = "월", selected = true)
            GsChip(text = "화", selected = false)
            GsChip(text = "수", selected = true)
            GsChip(text = "목", selected = false)
            GsChip(text = "금", selected = true)
            GsChip(text = "토", selected = false)
        }
    }
}

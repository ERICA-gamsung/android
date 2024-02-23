package com.erica.gamsung.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// timePicker 배경
@Suppress("MagicNumber")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TimeInputBox(timePickerState: TimePickerState) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(10))
                .background(Color.White),
        contentAlignment = Alignment.BottomCenter,
    ) {
        TimeInput(state = timePickerState)
    }
}

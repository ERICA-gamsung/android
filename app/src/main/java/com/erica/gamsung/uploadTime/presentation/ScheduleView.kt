package com.erica.gamsung.uploadTime.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun ScheduleView(
    // focusedDate: LocalDate? = null,
    // onDateSelected: ((LocalDate, Boolean) -> Unit)? = null,
) {
    var currentYearMonth by remember {
        mutableStateOf(YearMonth.now())
    }
    val selectedDatesMap =
        remember {
            mutableStateMapOf<YearMonth, List<LocalDate>>()
        }
    var lastMove by remember {
        mutableStateOf("START")
    }
    var check by remember {
        mutableStateOf(false)
    }

    fun moveToPreviousMonth() {
        if (lastMove == "RIGHT") {
            currentYearMonth = currentYearMonth.minusMonths(1)
            lastMove = "LEFT"
            check = true
        }
    }

    fun moveToNextMonth() {
        if (check || lastMove == "START") {
            currentYearMonth = currentYearMonth.plusMonths(1)
            lastMove = "RIGHT"
            check = false
        }
    }

    Column(
        horizontalAlignment =
            Alignment
                .CenterHorizontally,
        modifier =
            Modifier
                .background(color = Color.White)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(10.dp),
                ).clip(RoundedCornerShape(10.dp)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "좌측 이동",
                modifier = Modifier.clickable { moveToPreviousMonth() },
            )
            CalendarHeader(currentYearMonth)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "우측 이동",
                modifier = Modifier.clickable { moveToNextMonth() },
            )
        }
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 1.dp,
        )
        DaysOfWeekRow()
        CalendarGrid(
            yearMonth = currentYearMonth,
            selectedDatesMap = selectedDatesMap,
        )
    }
}

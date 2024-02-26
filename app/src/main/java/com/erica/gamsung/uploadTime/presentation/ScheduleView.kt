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
import androidx.compose.runtime.LaunchedEffect
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

@Suppress("LongMethod")
@Composable
fun ScheduleView(
    // focusedDate: LocalDate? = null,
    // onDateSelected: ((LocalDate, Boolean) -> Unit)? = null,
) {
    var currentYearMonth by remember {
        mutableStateOf(YearMonth.now())
    }
    val now = YearMonth.now()
    val selectedDatesMap =
        remember {
            mutableStateMapOf<YearMonth, List<LocalDate>>()
        }

    var moveDirection by remember {
        mutableStateOf(MoveDirection.START)
    }

    val canMoveLeft = currentYearMonth > now
    val canMoveRight = currentYearMonth == now

    /*
     *  Compose에서는 가능한 한 부작용(Side Effects)을 줄이고 순수 함수(pure functions)를 사용하는 것이 권장됩니다.
     *  LaunchedEffect나 rememberCoroutineScope를 사용하여 이벤트를 처리하는 것이 좋습니다.
     */
    LaunchedEffect(moveDirection) {
        when (moveDirection) {
            MoveDirection.LEFT -> if (canMoveLeft) currentYearMonth = currentYearMonth.minusMonths(1)
            MoveDirection.RIGHT -> if (canMoveRight) currentYearMonth = currentYearMonth.plusMonths(1)
            else -> {} // START 상태일 때는 변경 없음
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
                modifier = Modifier.clickable { moveDirection = MoveDirection.LEFT },
            )
            CalendarHeader(currentYearMonth)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "우측 이동",
                modifier = Modifier.clickable { moveDirection = MoveDirection.RIGHT },
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

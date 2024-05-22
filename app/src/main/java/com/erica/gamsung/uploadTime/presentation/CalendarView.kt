package com.erica.gamsung.uploadTime.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erica.gamsung.uploadTime.domain.CalendarMoveType
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

const val DATE_OF_FRAME = 42
const val FILTER_NUM = 32
const val CHUNK_NUM = 7

@Suppress("LongMethod")
@Composable
fun CalendarView(
    focusedDate: LocalDate? = null,
    selectedDatesMap: Map<YearMonth, List<LocalDate>>,
    onDateSelected: ((LocalDate, Boolean) -> Unit)? = null,
    onToggleValid: Boolean,
) {
    var currentYearMonth by remember {
        mutableStateOf(YearMonth.now())
    }
    val now = YearMonth.now()
//    val selectedDatesMap =
//        remember {
//            mutableStateMapOf<YearMonth, List<LocalDate>>()
//        }

    var moveDirection by remember {
        mutableStateOf(CalendarMoveType.START)
    }

    val canMoveLeft = currentYearMonth > now
    val canMoveRight = currentYearMonth == now

    /*
     *  Compose에서는 가능한 한 부작용(Side Effects)을 줄이고 순수 함수(pure functions)를 사용하는 것이 권장됩니다.
     *  LaunchedEffect나 rememberCoroutineScope를 사용하여 이벤트를 처리하는 것이 좋습니다.
     */
    LaunchedEffect(moveDirection) {
        when (moveDirection) {
            CalendarMoveType.LEFT -> if (canMoveLeft) currentYearMonth = currentYearMonth.minusMonths(1)
            CalendarMoveType.RIGHT -> if (canMoveRight) currentYearMonth = currentYearMonth.plusMonths(1)
            else -> {} // START 상태일 때는 변경 없음
        }
    }

    fun toggleDateSelectionInUI(
        date: LocalDate,
        isSelected: Boolean,
    ) {
        // UI에서 날짜 선택 토글을 처리하는 대신, 상위 컴포넌트에게 변경 사항을 알립니다.
        onDateSelected?.invoke(date, !isSelected)
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
                modifier = Modifier.clickable { moveDirection = CalendarMoveType.LEFT },
            )
            CalendarHeader(currentYearMonth)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "우측 이동",
                modifier = Modifier.clickable { moveDirection = CalendarMoveType.RIGHT },
            )
        }
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 1.dp,
        )
        DaysOfWeekRow()
        CalendarGrid(
            focusedDate = focusedDate,
            yearMonth = currentYearMonth,
            selectedDatesMap = selectedDatesMap,
            onDateSelected = { date, isSelected -> if (onToggleValid) toggleDateSelectionInUI(date, isSelected) },
        )
    }
}

// XXXX년 YY월
@Composable
fun CalendarHeader(yearMonth: YearMonth) {
    val formatter =
        remember {
            DateTimeFormatter
                .ofPattern("yyyy년 MM월")
        }
    Text(
        text =
            formatter
                .format(yearMonth),
        style =
            MaterialTheme
                .typography
                .headlineMedium,
        textAlign =
            TextAlign
                .Center,
        modifier =
            Modifier
                .padding(16.dp),
    )
}

// 요일 (일,월,화,수,목,금)
@OptIn(ExperimentalStdlibApi::class)
@Composable
fun DaysOfWeekRow() {
    val daysOfWeek =
        listOf(DayOfWeek.SUNDAY) +
            DayOfWeek
                .entries
                .filter { it != DayOfWeek.SUNDAY }
    Row {
        daysOfWeek
            .forEach { dayOfWeek ->
                Text(
                    text =
                        dayOfWeek
                            .getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    modifier =
                        Modifier
                            .padding(8.dp)
                            .weight(1f),
                    textAlign =
                        TextAlign
                            .Center,
                    style =
                        MaterialTheme
                            .typography
                            .bodyLarge,
                )
            }
    }
}

@Suppress("LongMethod", "MagicNumber")
@Composable
fun CalendarGrid(
    focusedDate: LocalDate? = null,
    yearMonth: YearMonth,
    selectedDatesMap: Map<YearMonth, List<LocalDate>>,
    onDateSelected: ((LocalDate, Boolean) -> Unit)? = null,
) {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstOfMonth = yearMonth.atDay(1)
    val prevMonthDays =
        (
            1 until
                firstOfMonth
                    .dayOfWeek
                    .value +
                1
        ).map { -it }
    val currentMonthDays =
        (1..daysInMonth)
            .toList()
    val nextMonthDays =
        List(
            DATE_OF_FRAME -
                (currentMonthDays.size + prevMonthDays.size),
        ) {
            FILTER_NUM
        }
    val calendarDays =
        (prevMonthDays + currentMonthDays + nextMonthDays)
            .chunked(CHUNK_NUM)
    calendarDays.forEachIndexed { _, week ->
        Row {
            week
                .forEach { day ->
                    if (day > 0 && day != FILTER_NUM) {
                        val date = yearMonth.atDay(day)
                        val isSelected = selectedDatesMap[yearMonth]?.contains(date) ?: false
                        DateView(
                            modifier =
                                Modifier
                                    .weight(1f),
                            date = date,
                            isSelected = isSelected,
                            focusedDate = focusedDate,
                            onDateSelected = { selectedDate, _ ->
                                // 선택된 날짜를 처리 하는 로직
                                onDateSelected?.invoke(selectedDate, !isSelected)
                                // 변경된 날짜 목록을 상위 Component 로 전달
                                Log.d("CalendarGrid", "Selected Date: $selectedDate, Is Selected: ${!isSelected}")
                                val mapContents =
                                    selectedDatesMap
                                        .entries
                                        .joinToString(separator = ", ", prefix = "{", postfix = "}") { (key, value) ->
                                            "$key=${value.joinToString()}"
                                        }
                                Log.d("CalendarGrid", "Current selectedDatesMap: $mapContents")
                            },
                        )
                    } else {
                        Text(
                            text = "",
                            modifier =
                                Modifier
                                    .padding(8.dp)
                                    .weight(1f),
                            style =
                                MaterialTheme
                                    .typography
                                    .bodyLarge,
                        )
                    }
                }
        }
    }
}

@Composable
fun DateView(
    modifier: Modifier = Modifier,
    date: LocalDate,
    focusedDate: LocalDate? = null,
    isSelected: Boolean,
    onDateSelected: ((LocalDate, Boolean) -> Unit)? = null,
) {
    val isFocused = date == focusedDate
    val backGroundColor =
        when {
            isFocused -> Color.Blue
            isSelected -> MaterialTheme.colorScheme.primary
            else -> Color.Transparent
        }
    val textColor = if (isSelected || isFocused) Color.White else MaterialTheme.colorScheme.onSurface

    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .padding(8.dp)
                .background(color = backGroundColor, shape = CircleShape)
                .then(
                    // page2 확장성을 위해 유지
                    if (date >= LocalDate.now()) {
                        Modifier.toggleable(
                            value = isSelected,
                            onValueChange = { newIsSelected ->
                                onDateSelected?.invoke(date, newIsSelected)
                            },
                        )
                    } else {
                        Modifier
                    },
                ),
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            color = textColor,
            textAlign =
                TextAlign
                    .Center,
            style =
                MaterialTheme
                    .typography
                    .bodyLarge,
        )
    }
}

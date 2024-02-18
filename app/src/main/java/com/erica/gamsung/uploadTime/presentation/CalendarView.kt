package com.erica.gamsung.uploadTime.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarView(
    selectedDates: List<LocalDate> = listOf(LocalDate.now()),
    // focusedDate: LocalDate? = null,
    onDateSelected: ((List<LocalDate>) -> Unit)? = null,
) {
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
        val yearMonth =
            remember(selectedDates) {
                if (selectedDates.isNotEmpty()) {
                    YearMonth.from(selectedDates.minOrNull())
                } else {
                    YearMonth.from(LocalDate.now())
                }
            }
        CalendarHeader(yearMonth)
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 1.dp,
        )
        DaysOfWeekRow()
        CalendarGrid(yearMonth, selectedDates, onDateSelected)
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
@Composable
fun DaysOfWeekRow() {
    val daysOfWeek =
        listOf(DayOfWeek.SUNDAY) +
            DayOfWeek
                .values()
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

@Suppress("LongMethod")
@Composable
fun CalendarGrid(
    yearMonth: YearMonth,
    selectedDates: List<LocalDate>,
    onDateSelected: ((List<LocalDate>) -> Unit)? = null,
) {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstOfMonth = yearMonth.atDay(1)
    val prevMonthDays =
        (
            1 until
                firstOfMonth
                    .dayOfWeek
                    .value
        ).map { -it }
    val currentMonthDays =
        (1..daysInMonth)
            .toList()
    val nextMonthDays =
        List(
            CalendarViewModel.DATE_OF_FRAME -
                (currentMonthDays.size + prevMonthDays.size),
        ) {
            CalendarViewModel
                .FILTER_NUM
        }
    val calendarDays =
        (prevMonthDays + currentMonthDays + nextMonthDays)
            .chunked(CalendarViewModel.CHUNK_NUM)
    calendarDays.forEachIndexed { _, week ->
        Row {
            week
                .forEach { day ->
                    if (day > 0 && day != CalendarViewModel.FILTER_NUM) {
                        DateView(
                            date =
                                yearMonth
                                    .atDay(day),
                            selectedDates = selectedDates,
                            onDateSelected = { selectedDate ->
                                // 선택된 날짜를 처리 하는 로직
                                if (onDateSelected != null) {
                                    val newSelectedDates =
                                        selectedDates.toMutableList().apply {
                                            // 이미 선택된 날짜면 제거, 아니면 추가
                                            if (contains(selectedDate)) {
                                                remove(selectedDate)
                                            } else {
                                                add(selectedDate)
                                            }
                                        }
                                    // 변경된 날짜 목록을 상위 Component 로 전달
                                    // onDateSelected: (List<LocalDate>) -> Unit
                                    onDateSelected(newSelectedDates)
                                }
                            },
                            modifier =
                                Modifier
                                    .weight(1f),
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
    selectedDates: List<LocalDate>,
    focusedDate: LocalDate? = null,
    onDateSelected: ((LocalDate) -> Unit)? = null,
) {
    val isSelected = remember(selectedDates, date) { selectedDates.contains(date) }
    val isFocused = date == focusedDate
    val backGroundColor =
        when {
            isSelected -> MaterialTheme.colorScheme.primary
            isFocused -> Color.Red
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
                    if (onDateSelected != null) {
                        Modifier.toggleable(
                            value = isSelected,
                            onValueChange = { onDateSelected(date) },
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

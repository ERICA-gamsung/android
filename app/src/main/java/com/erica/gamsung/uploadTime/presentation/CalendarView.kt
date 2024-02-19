package com.erica.gamsung.uploadTime.presentation

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    // focusedDate: LocalDate? = null,
    onDateSelected: ((LocalDate, Boolean) -> Unit)? = null,
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

    fun toggleDateSelection(
        date: LocalDate,
        isSelected: Boolean,
    ) {
        val currentSelectedDates = selectedDatesMap[currentYearMonth]?.toMutableList() ?: mutableListOf()
        if (isSelected) {
            currentSelectedDates.remove(date)
        } else {
            currentSelectedDates.add(date)
        }
        selectedDatesMap[currentYearMonth] = currentSelectedDates
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
            onDateSelected = { date, isSelected -> toggleDateSelection(date, isSelected) },
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

@Suppress("LongMethod", "MagicNumber")
@Composable
fun CalendarGrid(
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
                            modifier =
                                Modifier
                                    .weight(1f),
                            date =
                                yearMonth
                                    .atDay(day),
                            currentYearMonth = yearMonth,
                            selectedDatesMap = selectedDatesMap,
                            onDateSelected = { selectedDate, isSelected ->
                                // 선택된 날짜를 처리 하는 로직
                                onDateSelected?.invoke(selectedDate, !isSelected)
                                // 변경된 날짜 목록을 상위 Component 로 전달
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
    currentYearMonth: YearMonth,
    focusedDate: LocalDate? = null,
    selectedDatesMap: Map<YearMonth, List<LocalDate>>,
    onDateSelected: ((LocalDate, Boolean) -> Unit)? = null,
) {
    // UI의 즉각적인 변화를 위해 selectedDAtesMap[currentYearMonth] 직접 참조
    val isSelected =
        remember(
            selectedDatesMap[currentYearMonth],
            date,
        ) {
            selectedDatesMap[currentYearMonth]?.contains(date) ?: false
        }
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
                    // page2 확장성을 위해 유지
                    Modifier.toggleable(
                        value = isSelected,
                        onValueChange = { newIsSelected ->
                            onDateSelected?.invoke(date, newIsSelected)
                        },
                    ),
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

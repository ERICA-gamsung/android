package com.erica.gamsung.uploadTime.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit,
) {
    Column(
        horizontalAlignment =
            Alignment
                .CenterHorizontally,
        modifier =
            Modifier
                .background(color = Color.White),
    ) {
        val yearMonth =
            remember(selectedDate) { YearMonth.from(selectedDate) }
        CalendarHeader(yearMonth)
        DaysOfWeekRow()
        CalendarGrid(yearMonth, selectedDate, onDateSelected)
    }
}

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

@Composable
fun CalendarGrid(
    yearMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
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
                            selectedDate = selectedDate,
                            onDateSelected = onDateSelected,
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
    date: LocalDate,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = date.dayOfMonth.toString(),
        modifier =
            modifier
                .padding(8.dp)
                .toggleable(
                    value = date == selectedDate,
                    onValueChange = {
                        onDateSelected(date)
                    },
                ),
        textAlign =
            TextAlign
                .Center,
        style =
            MaterialTheme
                .typography
                .bodyLarge,
    )
}

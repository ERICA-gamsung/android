package com.erica.gamsung.uploadTime.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate

@Preview
@Composable
fun MyTimeScreen() {
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    CalendarView(selectedDate = selectedDate.value, onDateSelected = { date -> selectedDate.value })
}

package com.erica.gamsung.uploadTime.presentation

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.YearMonth

class CalendarViewModel : ViewModel() {
    val selectedDatesMap = mutableStateMapOf<YearMonth, List<LocalDate>>()

    init {
        val currentMonth = YearMonth.now()
        selectedDatesMap[currentMonth] = listOf(LocalDate.now())
    }

    fun toggleDateSelection(
        date: LocalDate,
        isSelected: Boolean,
    ) {
        val month = YearMonth.from(date)
        val updatedDates = selectedDatesMap[month]?.toMutableList() ?: mutableListOf()
        if (isSelected) {
            updatedDates.remove(date)
        } else {
            updatedDates.add(date)
        }
        selectedDatesMap[month] = updatedDates
    }
}

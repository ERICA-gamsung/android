package com.erica.gamsung.uploadTime.presentation

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.YearMonth

data class ScheduleData(
    val date: LocalDate,
    val time: String,
    val textOption: String,
    val message: String,
)

@Suppress("VariableNaming")
class CalendarViewModel : ViewModel() {
    val selectedDatesMap = mutableStateMapOf<YearMonth, List<LocalDate>>()

    private var _focusedDate = MutableLiveData<LocalDate?>()
    val focusedDate: LiveData<LocalDate?> = _focusedDate

    val scheduleDataMap = mutableStateMapOf<LocalDate, ScheduleData>()

    init {
        val currentMonth = YearMonth.now()
        selectedDatesMap[currentMonth] = listOf(LocalDate.now())
        _focusedDate.value = LocalDate.now()
    }

    fun setFocusedDate(date: LocalDate?) {
        _focusedDate.value = date
    }

    fun moveToNextDate() {
        val nextDate =
            selectedDatesMap.keys.minOrNull()?.let { currentMonth ->
                selectedDatesMap[currentMonth]?.let { dates ->
                    dates.firstOrNull { it > (_focusedDate.value ?: LocalDate.now()) }
                }
            }
        // MutableLiveData의 값을 업데이트하기 위해 setValue() 사용. 메인 스레드에서 호출
        // 혹은 postValue를 사용할 수 도 있음.(백그라운드 스레드에서 호출 가능)
        _focusedDate.value = nextDate
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

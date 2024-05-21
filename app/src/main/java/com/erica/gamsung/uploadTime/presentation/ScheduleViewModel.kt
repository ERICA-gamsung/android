package com.erica.gamsung.uploadTime.presentation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erica.gamsung.store.presentation.utils.toLocalTime
import com.erica.gamsung.uploadTime.data.repository.ScheduleRepository
import com.erica.gamsung.uploadTime.domain.ScheduleDataModel
import com.erica.gamsung.uploadTime.presentation.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("VariableNaming")
@HiltViewModel
class ScheduleViewModel
    @Inject
    constructor(
        // private val scheduleApi: ScheduleApi,
        private val repository: ScheduleRepository,
    ) : ViewModel() {
        val selectedDatesMap = mutableStateMapOf<YearMonth, MutableList<LocalDate>>()

        val scheduleDataModelMap = mutableStateMapOf<LocalDate, ScheduleDataModel>()

        private val _navigateToNextPage = MutableLiveData<Event<Unit>>()
        val navigateToNextPage: LiveData<Event<Unit>> = _navigateToNextPage

        private var _focusedDate = MutableLiveData<LocalDate?>()
        val focusedDate: LiveData<LocalDate?> = _focusedDate

        private var _selectTime = MutableLiveData<TimePickerState>()
        val selectTime: LiveData<TimePickerState> = _selectTime

//        private val _textOption = MutableLiveData("")
//        val textOption: LiveData<String> = _textOption

//        private val _message = MutableLiveData("")
//        val message: LiveData<String> = _message

        private val _uploadResult = MutableLiveData(false)
        val uploadResult: LiveData<Boolean> = _uploadResult

        init {
            val currentMonth = YearMonth.now()
            selectedDatesMap[currentMonth] = mutableListOf(LocalDate.now())
            _focusedDate.value = LocalDate.now()
        }

        fun resetUploadResult() {
            _uploadResult.value = false
        }

        fun setFocusedDate(date: LocalDate?) {
            _focusedDate.value = date
        }

        fun updateSelectedTime(newTime: TimePickerState) {
            _selectTime.value = newTime
        }

        fun moveToNextDate() {
            val currentFocusedDate = _focusedDate.value ?: LocalDate.now()
            val allDateAfterFocused =
                selectedDatesMap
                    .flatMap { entry ->
                        entry.value.filter { it > currentFocusedDate }
                    }.sorted()
            val nextDate = allDateAfterFocused.firstOrNull()
            if (nextDate == null) {
                _navigateToNextPage.value = Event(Unit)
                // _navigateTrigger.value = true
            } else {
                _focusedDate.value = nextDate
            }
            // MutableLiveData의 값을 업데이트하기 위해 setValue() 사용. 메인 스레드에서 호출
            // 혹은 postValue를 사용할 수 도 있음.(백그라운드 스레드에서 호출 가능)
        }

        fun moveToNextPage() {
            val dateList = selectedDatesMap.flatMap { it.value }.sorted()
            val nextDate = dateList.firstOrNull()
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
            if (updatedDates.isEmpty()) {
                selectedDatesMap.remove(month)
            } else {
                selectedDatesMap[month] = updatedDates
            }
            Log.d("내용 확인", "${selectedDatesMap[month]}")
            Log.d("내용 확인", "$selectedDatesMap")
        }

        // focusedDate.value(날짜) 를 date로 받는다.
        // 해당 Date에 엮인 Data들을 리스트에 추가해준다.
        @OptIn(ExperimentalMaterial3Api::class)
        fun updateScheduleData(
            time: TimePickerState?,
            menu: String,
            message: String,
            event: String,
        ) {
            focusedDate.value?.let { date ->
                val updatedScheduleDataModel =
                    ScheduleDataModel(
                        date = date,
                        time = time?.toLocalTime(),
                        menu = menu,
                        message = message,
                        event = event,
                    )
                scheduleDataModelMap[date] = updatedScheduleDataModel
            }
        }

        fun uploadSchedulesToServer() {
            viewModelScope.launch {
                try {
                    repository.uploadSchedules(scheduleDataModelMap).let { result ->
                        if (result.isSuccess) {
                            println("Schedules successfully uploaded")
                            _uploadResult.postValue(true)
                        } else {
                            println("Failed to upload schedules")
                            _uploadResult.postValue(false)
                        }
                    }
                } catch (e: HttpException) {
                    // Http 통신 중 발생한 예외 처리
                    println("HttpException: ${e.message}")
                    _uploadResult.postValue(false)
                } catch (e: IOException) {
                    // 네트워크 I/O 예외 처리
                    println("Network error: ${e.message}")
                    _uploadResult.postValue(false)
                }
            }
        }
    }

package com.erica.gamsung.uploadTime.presentation

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erica.gamsung.uploadTime.data.repository.ScheduleRepository
import com.erica.gamsung.uploadTime.domain.ScheduleDataList
import com.erica.gamsung.uploadTime.domain.ScheduleDataModel
import com.erica.gamsung.uploadTime.presentation.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@Suppress("VariableNaming")
@HiltViewModel
class ScheduleViewModel
    @Inject
    constructor(
        // private val scheduleApi: ScheduleApi,
        private val repository: ScheduleRepository,
    ) : ViewModel() {
        val selectedDatesMap = mutableStateMapOf<YearMonth, List<LocalDate>>()
        val scheduleDataModelMap = mutableStateMapOf<LocalDate, ScheduleDataModel>()

        private val _navigateToNextPage = MutableLiveData<Event<Unit>>()
        val navigateToNextPage: LiveData<Event<Unit>> = _navigateToNextPage

        private var _focusedDate = MutableLiveData<LocalDate?>()
        val focusedDate: LiveData<LocalDate?> = _focusedDate

        private val _textOption = MutableLiveData("")
        val textOption: LiveData<String> = _textOption

        private val _message = MutableLiveData("")
        val message: LiveData<String> = _message

        init {
            val currentMonth = YearMonth.now()
            selectedDatesMap[currentMonth] = listOf(LocalDate.now())
            _focusedDate.value = LocalDate.now()
        }

        fun setFocusedDate(date: LocalDate?) {
            _focusedDate.value = date
        }

        fun setTextOption(newValue: String) {
            _textOption.value = newValue
        }

        fun setMessage(newValue: String) {
            _message.value = newValue
        }

        fun moveToNextDate() {
            val currentFocusedDate = _focusedDate.value ?: LocalDate.now()
            val allDateAfterFocused =
                selectedDatesMap
                    .flatMap { entry ->
                        entry.value.filter { it > currentFocusedDate }
                    }.sorted()
            val nextDate = allDateAfterFocused.firstOrNull()
//        val nextDate =
//            selectedDatesMap.keys.minOrNull()?.let { currentMonth ->
//                selectedDatesMap[currentMonth]?.let { dates ->
//                    dates.firstOrNull { it > (_focusedDate.value ?: LocalDate.now()) }
//                }
//            }
            if (nextDate == null) {
                _navigateToNextPage.value = Event(Unit)
            } else {
                _focusedDate.value = nextDate
            }
            // MutableLiveData의 값을 업데이트하기 위해 setValue() 사용. 메인 스레드에서 호출
            // 혹은 postValue를 사용할 수 도 있음.(백그라운드 스레드에서 호출 가능)
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

        // focusedDate.value(날짜) 를 date로 받는다.
        // 해당 Date에 엮인 Data들을 리스트에 추가해준다.
        fun updateScheduleData(
            time: String,
            textOption: String,
            message: String,
        ) {
            focusedDate.value?.let { date ->
                val updatedScheduleDataModel =
                    ScheduleDataModel(
                        date = date,
                        time = time,
                        textOption = textOption,
                        message = message,
                    )
                scheduleDataModelMap[date] = updatedScheduleDataModel
            }
        }

        // 서버 전송을 위한 데이터 가공
        fun preparedScheduledDataForUpload(): ScheduleDataList {
            val scheduleDataList =
                scheduleDataModelMap.values.map { scheduleDataModel ->
                    ScheduleDataModel(
                        date = scheduleDataModel.date ?: LocalDate.now(),
                        time = scheduleDataModel.time,
                        textOption = scheduleDataModel.textOption,
                        message = scheduleDataModel.message,
                    )
                }
            return ScheduleDataList(scheduleList = scheduleDataList)
        }

        fun uploadSchedulesToServer() {
            viewModelScope.launch {
                try {
                    repository.uploadSchedules(scheduleDataModelMap).let { result ->
                        if (result.isSuccess) {
                            println("Schedules successfully uploaded")
                        } else {
                            println("Failed to upload schedules")
                        }
                    }
                } catch (e: HttpException) {
                    // Http 통신 중 발생한 예외 처리
                    println("HttpException: ${e.message}")
                } catch (e: IOException) {
                    // 네트워크 I/O 예외 처리
                    println("Network error: ${e.message}")
                }
            }
        }
    }

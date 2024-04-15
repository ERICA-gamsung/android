package com.erica.gamsung.post.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erica.gamsung.post.data.mock.mockSchedules
import com.erica.gamsung.post.domain.ScheduleList
import com.erica.gamsung.uploadTime.presentation.TimeSlotButton
import com.erica.gamsung.uploadTime.presentation.TitleTextSection

@Preview
@Composable
fun PostStatusScreen() {
    val scheduleList = mockSchedules
    var selectedTimeSlot by remember {
        mutableStateOf("")
    }
    Scaffold {
        Column(
            modifier =
                Modifier
                    .padding(it)
                    .padding(16.dp)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .size(75.dp),
            )
            TitleTextSection(text = "발행 중인 글 목록")
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                TimeSlotListSection(scheduleList = scheduleList, selectedTimeSlot = selectedTimeSlot) {
                    selectedTimeSlot = it
                }
            }
            Spacer(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .size(75.dp),
            )
        }
    }
}

@Composable
private fun TimeSlotListSection(
    scheduleList: ScheduleList,
    selectedTimeSlot: String?,
    onTimeSlotSelected: (String) -> Unit,
) {
    Column {
        scheduleList.schedules.forEach { schedule ->
            val slot = "${schedule.date} ${schedule.time}"
            TimeSlotButton(
                dateSlot = schedule.date,
                timeSlot = schedule.time,
                isSelected = slot == selectedTimeSlot,
                onTimeSlotSelected = { onTimeSlotSelected(slot) },
            )
            Spacer(modifier = Modifier.height(8.dp)) // 버튼 사이의 간격
        }
    }
}

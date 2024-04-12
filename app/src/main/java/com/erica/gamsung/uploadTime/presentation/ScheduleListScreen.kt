package com.erica.gamsung.uploadTime.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.core.presentation.Screen
import java.time.format.DateTimeFormatter

@Suppress("UnusedParameter")
@Preview
@Composable
fun ScheduleListScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: ScheduleViewModel = viewModel(),
) {
    // 여기서 remember를 사용하여 상태를 저장합니다.
    var selectedTimeSlot by remember { mutableStateOf("") }

    Scaffold {
        Column(
            modifier =
                Modifier
                    .padding(it)
                    .padding(16.dp)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .size(75.dp),
            )
            TitleTextSection(text = "선택한 시간이 맞습니까?")
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                TimeSlotListSection(calendarViewModel = viewModel, selectedTimeSlot = selectedTimeSlot) {
                    selectedTimeSlot = it
                }
                ButtonSection(viewModel) { navController.navigate(Screen.DateTimeFinish.route) }
            }
        }
    }
}

@Composable
fun TitleTextSection(text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Divider(modifier = Modifier.padding(bottom = 16.dp))
    }
}

@Composable
private fun ButtonSection(
    viewModel: ScheduleViewModel,
    onSuccess: () -> Unit = {},
) {
    val uploadResult by viewModel.uploadResult.observeAsState()
    Column {
        Divider(modifier = Modifier.padding(bottom = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(7.dp),
                onClick = { /* 선택 확인 액션 */ },
            ) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Button(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(7.dp),
                onClick = {
                    // 선택 확인 액션
                    viewModel.uploadSchedulesToServer()
                    onSuccess()
                },
            ) {
                Text("Confirm")
            }
            uploadResult?.let {
                if (it) {
                    onSuccess()
                } else {
                    Log.d("ScheduledListScreenLogic", "업로드 실패")
                }
            }
        }
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .size(50.dp),
        )
    }
}

@Composable
private fun TimeSlotListSection(
    calendarViewModel: ScheduleViewModel,
    selectedTimeSlot: String?,
    onTimeSlotSelected: (String) -> Unit,
) {
    // 시간 슬롯 목록, ViewModel에서 가져올 수 있습니다.
    val scheduleDataMap = calendarViewModel.scheduleDataModelMap

    Column {
        scheduleDataMap.forEach { (date, scheduleData) ->
            val dateText = scheduleData.date?.format(DateTimeFormatter.ofPattern("M월 d일")) ?: ""
            val timeText = scheduleData.time.toString()
            TimeSlotButton(
                dateSlot = dateText,
                timeSlot = timeText,
                isSelected = "$dateText $timeText" == selectedTimeSlot,
                onTimeSlotSelected = onTimeSlotSelected,
            )
            Spacer(modifier = Modifier.height(8.dp)) // 버튼 사이의 간격
        }
    }
}

@Suppress("MaxLineLength")
@Composable
fun TimeSlotButton(
    dateSlot: String,
    timeSlot: String,
    // selectedTimeSlot: String,
    isSelected: Boolean,
    onTimeSlotSelected: (String) -> Unit,
) {
    fun onCancel() {
        // TODO
    }
    Button(
        onClick = { onTimeSlotSelected("$dateSlot $timeSlot") },
        modifier =
            Modifier
                .fillMaxWidth()
                .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            ),
        // enabled = timeSlot != selectedTimeSlot
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(dateSlot)
            Text(timeSlot)
            Text("Cancel", modifier = Modifier.clickable { onCancel() })
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

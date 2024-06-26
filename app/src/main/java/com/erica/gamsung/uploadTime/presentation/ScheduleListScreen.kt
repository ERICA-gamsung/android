package com.erica.gamsung.uploadTime.presentation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.erica.gamsung.core.presentation.Screen
import com.erica.gamsung.core.presentation.theme.Blue
import java.time.format.DateTimeFormatter

@Composable
fun ScheduleListScreen(
    navController: NavHostController,
    viewModel: ScheduleViewModel = viewModel(),
) {
    BackHandler(enabled = true) {
//
    }
    // 여기서 remember를 사용하여 상태를 저장합니다.
    var selectedTimeSlot by remember { mutableStateOf("") }
    val showLastItemRemovalWarning by viewModel.showLastItemRemovalWarning.observeAsState()

    if (showLastItemRemovalWarning?.peekContent() == true) {
        AlertDialog(
            onDismissRequest = { viewModel.hideLastItemRemovalWarning() },
            confirmButton = {
                Button(
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Blue,
                        ),
                    onClick = {
                        viewModel.hideLastItemRemovalWarning()
                    },
                ) {
                    Text("확인")
                }
            },
            title = { Text("Warning!!") },
            text = { Text("마지막 시간 슬롯을 제거할 수 없습니다.") },
        )
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
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .size(75.dp),
            )
            TitleTextSection(text = "선택한 시간이 맞습니까?")
            Column(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                TimeSlotListSection(calendarViewModel = viewModel) {
                    selectedTimeSlot = it
                }
                ButtonSection(viewModel, navController) { navController.navigate(Screen.DateTimeFinish.route) }
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
        Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp))
    }
}

@Composable
private fun ButtonSection(
    viewModel: ScheduleViewModel,
    navController: NavHostController,
    onSuccess: () -> Unit = {},
) {
    val uploadResult by viewModel.uploadResult.observeAsState()

    Column {
        Divider(modifier = Modifier.padding(bottom = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(7.dp),
                onClick = {
                    navController.navigate(Screen.DateSelect.route) {
                        launchSingleTop = true
                        popUpTo(Screen.DateSelect.route) {
                            inclusive = false
                        }
                    }
                },
            ) {
                Text("취소하기", color = Color.Black)
            }
            Spacer(modifier = Modifier.padding(4.dp))
            OutlinedButton(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(7.dp),
                onClick = {
                    // 선택 확인 액션
                    viewModel.uploadSchedulesToServer()
                },
            ) {
                Text("확정하기", color = Color.Black)
            }
            uploadResult?.let {
                if (it) {
                    LaunchedEffect(Unit) {
                        onSuccess()
                        viewModel.resetUploadResult()
                    }
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
    // selectedTimeSlot: String?,
    onTimeSlotSelected: (String) -> Unit,
) {
    // 시간 슬롯 목록, ViewModel에서 가져올 수 있습니다.
    val scheduleDataMap = calendarViewModel.scheduleDataModelMap

    Column {
        scheduleDataMap.forEach { (_, scheduleData) ->
            val dateText = scheduleData.date?.format(DateTimeFormatter.ofPattern("M월 d일")) ?: ""
            val timeText = scheduleData.time.toString()
            TimeSlotButton(
                dateSlot = dateText,
                timeSlot = timeText,
                onTimeSlotSelected = onTimeSlotSelected,
                onCancel = { calendarViewModel.removeSchedule(scheduleData.date) },
                stateOption = null,
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
    onTimeSlotSelected: (String) -> Unit,
    onCancel: () -> Unit,
    stateOption: String?,
) {
    val borderColor = Color.Gray // Blue border when selected
    val backgroundColor = Color.Transparent
    OutlinedButton(
        onClick = { onTimeSlotSelected("$dateSlot $timeSlot") },
        modifier =
            Modifier
                .fillMaxWidth()
                .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = backgroundColor,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
        border = BorderStroke(1.dp, borderColor),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(dateSlot, style = MaterialTheme.typography.bodyMedium)
            Text(timeSlot, style = MaterialTheme.typography.bodyMedium)
            if (stateOption == null) {
                Icon(
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier.clickable { onCancel() },
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

package com.erica.gamsung.post.presentation

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.core.presentation.Screen
import com.erica.gamsung.post.domain.ScheduleState
import com.erica.gamsung.post.presentation.utils.formatDate
import com.erica.gamsung.post.presentation.utils.formatTime
import com.erica.gamsung.uploadTime.presentation.TimeSlotButton
import com.erica.gamsung.uploadTime.presentation.TitleTextSection

@Preview
@Composable
fun PostStatusScreen(
    navController: NavController = rememberNavController(),
    postViewModel: PostViewModel = hiltViewModel(),
) {
    val scheduleList by postViewModel.postListData.observeAsState()
    var isLoading by remember { mutableStateOf(true) }

    // 데이터 요청
    LaunchedEffect(key1 = true) {
        postViewModel.fetchPostListData()
        isLoading = false
    }

    // 데이터 로드 상태 감지 및 로그 출력
    LaunchedEffect(scheduleList) {
        Log.d("ScheduleList", "로드 성공")
        scheduleList?.let {
            Log.d("ScheduleList", "Data Loaded: $it")
            isLoading = false // 데이터가 로드되면 로딩 상태 업데이트
        }
    }

    var selectedTimeSlot by remember {
        mutableStateOf("")
    }

//    val reservationId by postViewModel.reservationId.observeAsState(0)

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
                TimeSlotListSection(
//                    scheduleStateList = mockScheduleList,
                    scheduleStateList = scheduleList,
                    navController = navController,
                    viewModel = postViewModel,
                    selectedTimeSlot = selectedTimeSlot,
                ) {
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

// ScheduleTStateList -> ScheduleStateList 로 변경해야 함.
@Composable
private fun TimeSlotListSection(
    viewModel: PostViewModel,
    navController: NavController,
    scheduleStateList: List<ScheduleState>?,
    selectedTimeSlot: String?,
    onTimeSlotSelected: (String) -> Unit,
) {
    val reservationId by viewModel.reservationId.observeAsState()
    Column {
        scheduleStateList?.forEach { schedule ->
            val slot = "${schedule.date} ${schedule.time}"
            TimeSlotButton(
                dateSlot = formatDate(schedule.date),
                timeSlot = formatTime(schedule.time),
                isSelected = slot == selectedTimeSlot,
                onTimeSlotSelected = {
                    onTimeSlotSelected(slot)
                    viewModel.selectReservation(schedule.reservationId)
                    Log.d("ResID", "resID: ${schedule.reservationId}")
                    Log.d("VM_ResID", "resID: ${viewModel.reservationId}")
                    Log.d("ObSt_ResID", "resID: $reservationId")
                    Log.d("VM_Hash", "Hash: ${viewModel.hashCode()}")
                    navController.navigate(Screen.SelectNewPost.route)
                },
                stateOption = schedule.state,
            )
            Spacer(modifier = Modifier.height(8.dp)) // 버튼 사이의 간격
        }
    }
}

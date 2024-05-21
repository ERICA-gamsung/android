package com.erica.gamsung.post.presentation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erica.gamsung.core.presentation.Screen
import com.erica.gamsung.core.presentation.component.GsTopAppBar
import com.erica.gamsung.post.domain.ScheduleState
import com.erica.gamsung.post.presentation.utils.formatDate
import com.erica.gamsung.post.presentation.utils.formatTime
import com.erica.gamsung.uploadTime.presentation.TitleTextSection

// PostStatusScreen은 어쩔 수 없음. 뺄게 없음 65/60
@Suppress("LongMethod")
@Composable
fun PostStatusScreen(
    navController: NavController,
    postViewModel: PostViewModel = hiltViewModel(),
) {
    val scheduleList by postViewModel.postListData.observeAsState()
    var isLoading by remember { mutableStateOf(true) }
    var selectedStatus by remember { mutableStateOf("All") }

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
    // Filtering 한 리스트
    val filteredList =
        scheduleList?.filter {
            when (selectedStatus) {
                "All" -> true
                "Ready" -> it.state == "ready"
                "Done" -> it.state == "done"
                else -> true
            }
        } ?: listOf()
    Scaffold(
        topBar = {
            GsTopAppBar(
                title = "",
                hasLeftIcon = true,
                onNavigationClick = {
                    navController.navigate(Screen.Main.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = false
                        }
                    }
                },
            )
        },
    ) {
        Column(
            modifier =
                Modifier
                    .padding(it)
                    .padding(16.dp)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleTextSection(text = "발행 중인 글 목록")
            StatusFilterButtons(selectedStatus = selectedStatus) { status ->
                selectedStatus = status
            }
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                // verticalArrangement = Arrangement.SpaceBetween
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TimeSlotListSection(
                    scheduleStateList = filteredList,
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

// Color 값 때문인데, 어쩔 수 없음
@Suppress("MagicNumber")
@Composable
fun StatusFilterButtons(
    selectedStatus: String,
    onStatusSelected: (String) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        val statuses = listOf("All", "Ready", "Done")
        statuses.forEach { status ->
            val backgroundColor = if (selectedStatus == status) Color(0x403399FF) else Color.Transparent
            val contentColor = if (selectedStatus == status) Color(0xFF3399FF) else Color.Black
            val borderColor = if (selectedStatus == status) Color(0xFF3399FF) else Color.Gray

            OutlinedButton(
                onClick = { onStatusSelected(status) },
                colors =
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = backgroundColor,
                        contentColor = contentColor,
                    ),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, borderColor),
                modifier = Modifier.padding(horizontal = 4.dp),
            ) {
                if (status == "All") {
                    Icon(imageVector = Icons.Default.FilterList, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(text = status)
            }
        }
    }
}

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
            EnhancedTimeSlotButton(
                dateSlot = formatDate(schedule.date),
                timeSlot = formatTime(schedule.time),
                isSelected = slot == selectedTimeSlot,
                onTimeSlotSelected = {
                    if (schedule.state == "yet") {
                        onTimeSlotSelected(slot)
                        viewModel.selectReservation(schedule.reservationId)
                        Log.d("ResID", "resID: ${schedule.reservationId}")
                        Log.d("VM_ResID", "resID: ${viewModel.reservationId}")
                        Log.d("ObSt_ResID", "resID: $reservationId")
                        Log.d("VM_Hash", "Hash: ${viewModel.hashCode()}")
                        navController.navigate(Screen.SelectNewPost.route)
                    }
                },
                stateOption = schedule.state,
            )
            Spacer(modifier = Modifier.height(8.dp)) // 버튼 사이의 간격
        }
    }
}

// Color 값 때문인데, 어쩔 수 없음
@Suppress("MagicNumber")
@Composable
fun EnhancedTimeSlotButton(
    dateSlot: String,
    timeSlot: String,
    isSelected: Boolean,
    onTimeSlotSelected: () -> Unit,
    stateOption: String?,
) {
    val borderColor = if (isSelected) Color(0xFF3399FF) else Color.Gray // Blue border when selected
    val backgroundColor =
        if (isSelected) {
            Color(
                0xC03399FF,
            )
        } else {
            Color.Transparent // Transparent blue background with 50% opacity when selected
        }

    OutlinedButton(
        onClick = onTimeSlotSelected,
        modifier =
            Modifier
                .fillMaxWidth()
                .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = backgroundColor,
                contentColor =
                    if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
            ),
        border = BorderStroke(1.dp, borderColor),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(dateSlot, style = MaterialTheme.typography.bodyMedium)
            Text(timeSlot, style = MaterialTheme.typography.bodySmall)
            if (stateOption == null) {
                Text("Cancel", modifier = Modifier.clickable { /* onCancel logic */ })
            } else {
                Text(stateOption)
            }
        }
    }
}

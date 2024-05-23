package com.erica.gamsung.uploadTime.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.erica.gamsung.core.presentation.Screen
import com.erica.gamsung.core.presentation.component.GsButton
import com.erica.gamsung.core.presentation.component.GsTopAppBar

@Suppress("magicnumber")
@Composable
fun MyCalendarScreen(
    navController: NavHostController,
    viewModel: ScheduleViewModel = viewModel(),
) {
    // 각 달별로 선택된 날짜들을 관리하기 위한 상태 맵
    // val selectedDatesMap = remember { mutableStateMapOf<YearMonth, List<LocalDate>>() }
    // 현재 달을 기준으로 초기화
    // val currentMonth = YearMonth.now()
    // 현재 달에 대한 초기 선택된 날짜 리스트 (예: 오늘)
    // selectedDatesMap[currentMonth] = listOf(LocalDate.now())

    Scaffold(
        topBar = {
            GsTopAppBar(
                title = "날짜 선택",
                hasLeftIcon = true,
                onNavigationClick = { navController.navigate(Screen.Main.route) },
            )
        },
    ) {
        Column(
            modifier =
                Modifier
                    .padding(it)
                    .padding(16.dp)
                    .fillMaxSize(),
            horizontalAlignment =
                Alignment
                    .CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CalendarView(
                selectedDatesMap = viewModel.selectedDatesMap,
                onDateSelected = { date, isSelected ->
                    viewModel.toggleDateSelection(date, isSelected)
                },
                onToggleValid = true,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "달력에서 원하시는 발행일을 선택해 주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(32.dp))
            GsButton(text = "확정하기", onClick = {
                viewModel.moveToNextPage()
                navController.navigate("timeSelect")
            }, containerColor = Color.Blue)
        }
    }
}

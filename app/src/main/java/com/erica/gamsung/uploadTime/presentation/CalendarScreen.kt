package com.erica.gamsung.uploadTime.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erica.gamsung.core.presentation.component.GsButton
import java.time.LocalDate

@Suppress("magicnumber")
@Preview
@Composable
fun MyCalendarScreen() {
    val selectedDates = remember { mutableStateOf(listOf(LocalDate.now())) }
    Scaffold {
        Column(
            modifier =
                Modifier
                    .padding(it)
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
            horizontalAlignment =
                Alignment
                    .CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CalendarView(selectedDates = selectedDates.value, onDateSelected = { dates -> selectedDates.value = dates })
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "달력에서 원하시는 발행일을 선택해 주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(32.dp))
            GsButton(text = "확정하기", onClick = {
                // val selectedDatesString = selectedDates.value.joinToString(separator = ",") { it.toString() }
                // navController.navigate("scheduleScreen/$selectedDatesString")
            })
        }
    }
}

package com.erica.gamsung.uploadTime.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.erica.gamsung.core.presentation.component.DropdownInputTextBox
import com.erica.gamsung.core.presentation.component.GsButton
import com.erica.gamsung.core.presentation.component.GsOutlinedButton
import com.erica.gamsung.core.presentation.component.InputTextBox
import com.erica.gamsung.core.presentation.component.TextTitle
import com.erica.gamsung.core.presentation.component.TimeInputBox
import com.erica.gamsung.store.presentation.utils.toDisplayString
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("LongMethod")
@Preview
@Composable
fun MyScheduleScreen() {
    // 각 달별로 선택된 날짜들을 관리하기 위한 상태 맵
    val selectedDatesMap = remember { mutableStateMapOf<YearMonth, List<LocalDate>>() }
    // 현재 달을 기준으로 초기화
    val currentMonth = YearMonth.now()
    // 현재 달에 대한 초기 선택된 날짜 리스트 (예: 오늘)
    selectedDatesMap[currentMonth] = listOf(LocalDate.now())

    // 사용자 입력을 저장할 상태 변수
    var text by remember { mutableStateOf("") }

    // TimePicker에 필요한 변수
    var openTimePickerState = TimePickerState(0, 0, false)
    var onOpenTimeUpdate: (TimePickerState) -> Unit = { /*TODO*/ }

    val options =
        listOf(
            "option1", "option2", "option3", "option1",
            "option2", "option3", "option1", "option2",
            "option3", "option1", "option2", "option3",
        )

    Scaffold {
        Column(
            modifier =
                Modifier
                    .padding(it)
                    .padding(16.dp)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ScheduleView()
            // Spacer(modifier = Modifier.height(16.dp))

            HoursSection(
                title = "발행 시각",
                timePickerState = openTimePickerState,
                onUpdate = onOpenTimeUpdate,
                modifier = Modifier,
            )
            DropTitleTextField(
                modifier =
                    Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                title = "강조 메뉴",
                hintText = "선택 없음",
                description = (" (선택)"),
                onValueChange = { newText ->
                    text = newText
                },
                items = options,
                isValid = false,
            )
            // Spacer(modifier = Modifier.height(8.dp))
            TitleTextField(
                modifier =
                    Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                title = "고객에게 전하고 싶은 메세지",
                description = (" (선택)"),
                hintText = "ex. 서비스가 괜찮아요",
                onValueChange = { newText ->
                    text = newText
                },
                keyboardType = KeyboardType.Text,
                isValid = false,
            )
            // Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                GsOutlinedButton(text = "취소하기")
                Spacer(modifier = Modifier.width(32.dp))
                GsButton(text = "선택하기", containerColor = Color.Blue)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HoursSection(
    title: String,
    timePickerState: TimePickerState?,
    onUpdate: (TimePickerState) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showTimePicker by remember { mutableStateOf(false) }
    val currentTimePickerState = remember { timePickerState ?: TimePickerState(0, 0, false) }

    if (showTimePicker) {
        Dialog(
            onDismissRequest = {
                onUpdate(currentTimePickerState)
                showTimePicker = false
            },
        ) {
            TimeInputBox(currentTimePickerState)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        TextTitle(
            title = title,
            isRequired = true,
            description = " (기본값은 추천 시간입니다)",
            modifier = Modifier.padding(top = 10.dp),
        )
        TextButton(
            onClick = {
                showTimePicker = true
            },
            colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color.Gray),
        ) {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = title,
                modifier = Modifier.padding(end = 10.dp),
            )
            Text(
                text = timePickerState?.toDisplayString() ?: "선택 안함",
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun TitleTextField(
    modifier: Modifier = Modifier,
    title: String,
    hintText: String,
    description: String?,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isValid: Boolean,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        TextTitle(
            title = title,
            isRequired = isValid,
            description = description,
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            InputTextBox(
                modifier =
                    Modifier
                        .padding(top = 5.dp, end = 10.dp)
                        .fillMaxWidth(),
                hintText = hintText,
                onValueChange = onValueChange,
                keyboardType = keyboardType,
            )
        }
    }
}

@Composable
private fun DropTitleTextField(
    modifier: Modifier = Modifier,
    title: String,
    hintText: String,
    description: String?,
    onValueChange: (String) -> Unit,
    items: List<String>,
    isValid: Boolean,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        TextTitle(
            title = title,
            isRequired = isValid,
            description = description,
        )
        DropdownInputTextBox(
            modifier =
                Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
            hintText = hintText,
            items = items,
            onValueChange = onValueChange,
        )
    }
}

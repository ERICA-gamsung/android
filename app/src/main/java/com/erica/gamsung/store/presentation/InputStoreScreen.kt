package com.erica.gamsung.store.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.core.presentation.Screen
import com.erica.gamsung.core.presentation.component.GsButton
import com.erica.gamsung.core.presentation.component.GsChip
import com.erica.gamsung.core.presentation.component.GsTopAppBar
import com.erica.gamsung.core.presentation.component.TextTitle
import com.erica.gamsung.core.presentation.component.TimeInputBox
import com.erica.gamsung.store.domain.StoreType
import com.erica.gamsung.store.presentation.components.TitleTextField
import com.erica.gamsung.store.presentation.utils.toDisplayString
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputStoreScreen(
    navController: NavHostController = rememberNavController(),
    storeViewModel: StoreViewModel = hiltViewModel(),
    isEditMode: Boolean = false,
) {
    val inputStoreState by storeViewModel.inputStoreState.collectAsState()

    Scaffold(
        topBar = { GsTopAppBar(title = if (isEditMode) "식당 정보 수정" else "식당 정보 입력 (1/2)") },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding(), start = 8.dp, end = 8.dp),
        ) {
            StoreNameSection(onValueChange = { storeViewModel.onEvent(InputStoreUiEvent.NameChanged(it)) })
            StoreTypeSection(
                onClick = { storeViewModel.onEvent(InputStoreUiEvent.TypeChanged(it)) },
                selectedStoreType = inputStoreState.type,
            )
            StoreBusinessHoursSection(
                openTimePickerState = inputStoreState.openTime,
                closeTimePickerState = inputStoreState.closeTime,
                onOpenTimeUpdate = { storeViewModel.onEvent(InputStoreUiEvent.OpenTimeUpdate(it)) },
                onCloseTimeUpdate = { storeViewModel.onEvent(InputStoreUiEvent.CloseTimeUpdate(it)) },
            )
            StoreBusinessDaysSection(
                onClick = { storeViewModel.onEvent(InputStoreUiEvent.BusinessDaysChanged(it)) },
                week = inputStoreState.businessDays,
            )
            StoreAddressSection(onValueChange = { storeViewModel.onEvent(InputStoreUiEvent.AddressChanged(it)) })
            StorePhoneNumberSection(onValueChange = {
                storeViewModel.onEvent(InputStoreUiEvent.PhoneNumberChanged(it))
            })
            Spacer(modifier = Modifier.weight(1f))
            RegisterStoreButton(
                onClick = {
                    storeViewModel.onEvent(InputStoreUiEvent.SendStore)
                    navController.navigate(Screen.InputMenu(isEditMode = false).route)
                },
                enabled = inputStoreState.isValid,
                isEditMode = isEditMode,
            )
        }
    }
}

@Composable
fun StoreNameSection(
    onValueChange: (String) -> Unit = {},
    isValid: Boolean = true,
) {
    TitleTextField(
        title = "음식점 이름",
        hintText = "ex. 감성식당",
        onValueChange = onValueChange,
        isValid = isValid,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun StoreTypeSection(
    onClick: (StoreType) -> Unit = {},
    selectedStoreType: StoreType? = null,
) {
    TextTitle(
        title = "업종",
        isRequired = true,
        description = null,
        modifier = Modifier.padding(top = 10.dp),
    )
    Row {
        StoreType.entries.forEach { storeType ->
            GsChip(
                text = storeType.description,
                selected = storeType == selectedStoreType,
                modifier = Modifier.padding(end = 10.dp),
                onClick = { onClick(storeType) },
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun StoreBusinessHoursSection(
    openTimePickerState: TimePickerState?,
    closeTimePickerState: TimePickerState?,
    onOpenTimeUpdate: (TimePickerState) -> Unit,
    onCloseTimeUpdate: (TimePickerState) -> Unit,
) {
    TextTitle(
        title = "영업 시간",
        isRequired = true,
        description = null,
        modifier = Modifier.padding(top = 10.dp),
    )

    Row {
        HoursSection(
            timePickerState = openTimePickerState,
            onUpdate = onOpenTimeUpdate,
            modifier = Modifier.weight(1f),
            title = "시작시간",
        )
        HoursSection(
            timePickerState = closeTimePickerState,
            onUpdate = onCloseTimeUpdate,
            modifier = Modifier.weight(1f),
            title = "종료시간",
        )
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
        modifier = modifier.padding(end = 40.dp),
    ) {
        TextTitle(
            title = title,
            isRequired = false,
            description = null,
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

@OptIn(ExperimentalStdlibApi::class)
@Composable
private fun StoreBusinessDaysSection(
    onClick: (DayOfWeek) -> Unit = {},
    week: Map<DayOfWeek, Boolean> =
        DayOfWeek.entries.associateWith { false },
) {
    TextTitle(
        title = "영업일",
        isRequired = true,
        description = null,
        modifier = Modifier.padding(top = 10.dp),
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        week.forEach { (day, isSelected) ->
            GsChip(
                text = day.getDisplayName(TextStyle.SHORT, Locale.KOREA),
                selected = isSelected,
                onClick = { onClick(day) },
            )
        }
    }
}

@Composable
private fun StoreAddressSection(
    onValueChange: (String) -> Unit = {},
    isValid: Boolean = true,
) {
    TitleTextField(
        title = "주소",
        hintText = "ex. 안산시 상록구 한양대학로 99 1층",
        onValueChange = onValueChange,
        isValid = isValid,
    )
}

@Composable
private fun StorePhoneNumberSection(
    onValueChange: (String) -> Unit = {},
    isValid: Boolean = true,
) {
    TitleTextField(
        title = "전화 번호",
        hintText = "ex. 010-1234-1234",
        onValueChange = onValueChange,
        isValid = isValid,
        keyboardType = KeyboardType.Number,
    )
}

@Composable
private fun RegisterStoreButton(
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    isEditMode: Boolean = false,
) {
    GsButton(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(vertical = 12.dp),
        text = if (isEditMode) "가게 수정하기" else "가게 등록하기",
        onClick = onClick,
        enabled = enabled,
    )
}

@Preview
@Composable
private fun InputStoreScreenPreview() {
    InputStoreScreen()
}

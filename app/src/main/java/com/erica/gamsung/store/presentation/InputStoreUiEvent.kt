package com.erica.gamsung.store.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.erica.gamsung.store.domain.StoreType
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
sealed interface InputStoreUiEvent {
    data class NameChanged(
        val name: String,
    ) : InputStoreUiEvent

    data class AddressChanged(
        val address: String,
    ) : InputStoreUiEvent

    data class PhoneNumberChanged(
        val phoneNumber: String,
    ) : InputStoreUiEvent

    data class TypeChanged(
        val type: StoreType,
    ) : InputStoreUiEvent

    data class BusinessDaysChanged(
        val day: DayOfWeek,
    ) : InputStoreUiEvent

    data class OpenTimeUpdate(
        val timePickerState: TimePickerState,
    ) : InputStoreUiEvent

    data class CloseTimeUpdate(
        val timePickerState: TimePickerState,
    ) : InputStoreUiEvent
}

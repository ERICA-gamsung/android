package com.erica.gamsung.store.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.erica.gamsung.store.domain.StoreType
import java.time.DayOfWeek

@OptIn(ExperimentalStdlibApi::class, ExperimentalMaterial3Api::class)
data class InputStoreState(
    val name: String? = null,
    val address: String? = null,
    val phoneNumber: String? = null,
    val type: StoreType? = null,
    val businessDays: Map<DayOfWeek, Boolean> =
        DayOfWeek.entries.associateWith { false },
    val openTime: TimePickerState? = null,
    val closeTime: TimePickerState? = null,
)

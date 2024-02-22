package com.erica.gamsung.store.presentation.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState

@Suppress("ImplicitDefaultLocale", "MagicNumber")
@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.toDisplayString(): String {
    val time = StringBuilder()
    time.append(if (this.hour < 12) "AM" else "PM")
    time.append(String.format(" %02d", if (this.hour % 12 == 0) 12 else this.hour % 12))
    time.append(String.format(":%02d", this.minute))
    return time.toString()
}

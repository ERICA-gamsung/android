@file:OptIn(ExperimentalMaterial3Api::class)

package com.erica.gamsung.store.presentation.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.time.LocalTime

@Suppress("ImplicitDefaultLocale", "MagicNumber")
fun TimePickerState.toDisplayString(): String {
    val time = StringBuilder()
    time.append(if (this.hour < 12) "AM" else "PM")
    time.append(String.format(" %02d", if (this.hour % 12 == 0) 12 else this.hour % 12))
    time.append(String.format(":%02d", this.minute))
    return time.toString()
}

fun TimePickerState.toLocalTime(): LocalTime = LocalTime.of(hour, minute)

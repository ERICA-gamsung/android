package com.erica.gamsung.store.domain

import java.time.DayOfWeek
import java.time.LocalTime

data class Store(
    val name: String,
    val address: String,
    val phoneNumber: String,
    val type: StoreType,
    val businessDays: Map<DayOfWeek, Boolean>,
    val openTime: LocalTime,
    val closeTime: LocalTime,
)

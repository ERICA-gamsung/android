package com.erica.gamsung.post.domain

import java.time.LocalDate
import java.time.LocalTime

data class Schedule(
    val reservationId: Int,
    val date: LocalDate,
    val time: LocalTime,
    val contents: List<String>,
    val fixedContent: String,
    val imageUrl: List<String>,
)

data class ScheduleState(
    val reservationId: Int,
    val date: LocalDate,
    val time: LocalTime,
    val state: String,
)

data class ScheduleTState(
    val reservationId: Int,
    val date: String,
    val time: String,
    val state: String,
)

data class ScheduleList(
    val schedules: List<Schedule>,
)

data class ScheduleStateList(
    val scheduleState: List<ScheduleState>,
)

data class ScheduleTStateList(
    val scheduleState: List<ScheduleTState>,
)

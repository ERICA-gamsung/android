package com.erica.gamsung.post.domain

data class Schedule(
    val reservationId: Int,
    val date: String,
    val time: String,
    val menu: String,
    val event: String,
    val message: String,
)

data class ScheduleList(
    val schedules: List<Schedule>,
)

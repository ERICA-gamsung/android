package com.erica.gamsung.post.domain

import com.erica.gamsung.post.data.mock.Post

data class Schedule(
    val reservationId: Int,
    val date: String,
    val time: String,
    val contents: List<Post>,
    val fixedContent: String,
    val imageUrl: List<String>,
)

data class ScheduleList(
    val schedules: List<Schedule>,
)

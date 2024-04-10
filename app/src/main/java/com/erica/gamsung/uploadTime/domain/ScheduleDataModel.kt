package com.erica.gamsung.uploadTime.domain

import java.time.LocalDate
import java.time.LocalTime

data class ScheduleDataModel(
    val date: LocalDate?,
    val time: LocalTime?,
    val menu: String,
    val event: String?,
    val message: String,
)

data class ScheduleDataList(
    val scheduleList: List<ScheduleDataModel>,
)

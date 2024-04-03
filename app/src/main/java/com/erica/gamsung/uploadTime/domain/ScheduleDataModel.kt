package com.erica.gamsung.uploadTime.domain

import java.time.LocalDate

data class ScheduleDataModel(
    val date: LocalDate?,
    val time: String,
    val menu: String,
    val event: String?,
    val message: String,
)

data class ScheduleDataList(
    val scheduleList: List<ScheduleDataModel>,
)

package com.erica.gamsung.uploadTime.data.remote

import java.time.LocalDate
import java.time.LocalTime

data class PostScheduleDataResponseModel(
    val reservationId: Int,
    val date: LocalDate?,
    val time: LocalTime?,
    val menu: String,
    val event: String,
    val message: String,
)

data class PostScheduleDataRequestModel(
    val date: LocalDate?,
    val time: LocalTime?,
    val menu: String,
    val event: String,
    val message: String,
)

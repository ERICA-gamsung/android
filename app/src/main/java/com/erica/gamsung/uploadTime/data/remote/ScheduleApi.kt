package com.erica.gamsung.uploadTime.data.remote

import com.erica.gamsung.uploadTime.domain.ScheduleDataList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ScheduleApi {
    @POST("/api/schedules")
    suspend fun uploadSchedules(
        @Body scheduleDataList: ScheduleDataList,
    ): Response<ApiResponse>
}

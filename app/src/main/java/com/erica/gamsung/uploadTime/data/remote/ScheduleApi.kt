package com.erica.gamsung.uploadTime.data.remote

import com.erica.gamsung.uploadTime.domain.ScheduleDataList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ScheduleApi {
    @POST("/api/schedules")
    fun uploadSchedules(
        @Body scheduleDataList: ScheduleDataList,
    ): Call<ApiResponse>
}

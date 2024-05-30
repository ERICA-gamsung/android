package com.erica.gamsung.uploadTime.data.remote

import com.erica.gamsung.uploadTime.domain.ScheduleDataModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ScheduleApi {
    @POST("postings/option")
    suspend fun uploadSchedules(
        @Body scheduleDataList: List<ScheduleDataModel>,
        @Header("Authorization") bearerToken: String,
    ): Response<List<PostScheduleDataResponseModel>>
}

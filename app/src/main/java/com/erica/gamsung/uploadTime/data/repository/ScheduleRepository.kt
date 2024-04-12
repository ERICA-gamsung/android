package com.erica.gamsung.uploadTime.data.repository

import com.erica.gamsung.uploadTime.data.remote.ScheduleApi
import com.erica.gamsung.uploadTime.domain.ScheduleDataModel
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import javax.inject.Inject

class ScheduleRepository
    @Inject
    constructor(
        private val scheduleApi: ScheduleApi,
    ) {
        suspend fun uploadSchedules(scheduleDataModelMap: Map<LocalDate, ScheduleDataModel>): Result<Boolean> =
            try {
                val scheduleDataList =
                    scheduleDataModelMap.values.map { scheduleDataModel ->
                        ScheduleDataModel(
                            date = scheduleDataModel.date,
                            time = scheduleDataModel.time,
                            menu = scheduleDataModel.menu,
                            message = scheduleDataModel.message,
                            event = scheduleDataModel.event,
                        )
                    }
                val response = scheduleApi.uploadSchedules(scheduleDataList)
                if (response.isSuccessful) {
                    val allSuccess = response.body()?.all { it.success } ?: false
                    if (allSuccess) {
                        Result.success(true)
                    } else {
                        Result.failure(Exception("Not all schedules were successfully uploaded"))
                    }
                } else {
                    Result.failure(Exception("Failed to upload schedules, HTTP error"))
                }
            } catch (e: IOException) {
                Result.failure(Exception("Network error: ${e.message}"))
            } catch (e: HttpException) {
                Result.failure(Exception("HTTP error: ${e.message}"))
            }
    }

package com.erica.gamsung.uploadTime.data.repository

import com.erica.gamsung.uploadTime.data.remote.ScheduleApi
import com.erica.gamsung.uploadTime.domain.ScheduleDataList
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
                            event = null,
                        )
                    }
                val response = scheduleApi.uploadSchedules(ScheduleDataList(scheduleList = scheduleDataList))
                if (response.isSuccessful && response.body()?.success == true) {
                    Result.success(true)
                } else {
                    Result.failure(Exception("Failed to upload schedules"))
                }
            } catch (e: IOException) {
                Result.failure(Exception("Network error: ${e.message}"))
            } catch (e: HttpException) {
                Result.failure(Exception("HTTP error: ${e.message}"))
            }
    }

package com.erica.gamsung.uploadTime.data.repository

import com.erica.gamsung.uploadTime.data.remote.PostScheduleDataResponseModel
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
                val scheduleDataList = prepareDataList(scheduleDataModelMap)
                val response = scheduleApi.uploadSchedules(scheduleDataList)
                handleResponse(response)
            } catch (e: IOException) {
                Result.failure(Exception("Network error: ${e.message}"))
            } catch (e: HttpException) {
                Result.failure(Exception("HTTP error: ${e.message}"))
            }

        private fun prepareDataList(scheduleDataModelMap: Map<LocalDate, ScheduleDataModel>): List<ScheduleDataModel> =
            scheduleDataModelMap.values.map { scheduleDataModel ->
                ScheduleDataModel(
                    date = scheduleDataModel.date,
                    time = scheduleDataModel.time,
                    menu = scheduleDataModel.menu,
                    message = scheduleDataModel.message,
                    event = scheduleDataModel.event,
                )
            }

        private fun handleResponse(response: retrofit2.Response<List<PostScheduleDataResponseModel>>): Result<Boolean> {
            println("HTTP status: ${response.code()}")
            if (!response.isSuccessful) {
                println("Response error body: ${response.errorBody()?.string()}")
                return Result.failure(Exception("Failed to upload schedules, HTTP error"))
            }
            val body = response.body()
            val isSuccessful = !body.isNullOrEmpty()
            println("All time Success: $isSuccessful")
            return if (isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Not all schedules were successfully uploaded"))
            }
        }
    }

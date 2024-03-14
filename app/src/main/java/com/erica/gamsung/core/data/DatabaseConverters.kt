package com.erica.gamsung.core.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.DayOfWeek
import java.time.LocalTime

class DatabaseConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromLocalTime(localTime: LocalTime): String = localTime.toString()

    @TypeConverter
    fun toLocalTime(data: String): LocalTime = LocalTime.parse(data)

    @TypeConverter
    fun fromDayOfWeekMap(dayOfWeekMap: Map<DayOfWeek, Boolean>): String {
        val type = object : TypeToken<Map<DayOfWeek, Boolean>>() {}.type
        return gson.toJson(dayOfWeekMap, type)
    }

    @TypeConverter
    fun toDayOfWeekMap(data: String): Map<DayOfWeek, Boolean> {
        val type = object : TypeToken<Map<DayOfWeek, Boolean>>() {}.type
        return gson.fromJson(data, type)
    }
}

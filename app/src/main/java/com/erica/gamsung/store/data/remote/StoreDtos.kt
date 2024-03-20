package com.erica.gamsung.store.data.remote

import com.erica.gamsung.store.domain.Store
import com.erica.gamsung.store.domain.StoreType
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

data class GetStoreResponse(
    val id: Long,
    val name: String,
    val type: String,
    val openTime: LocalTime,
    val closeTime: LocalTime,
    val openDay: String,
    val address: String,
    val phoneNumber: String,
) {
    fun toDomainModel(): Store {
        return Store(
            name = name,
            type = StoreType.from(type),
            openTime = openTime,
            closeTime = closeTime,
            businessDays = getBusinessDays(),
            address = address,
            phoneNumber = phoneNumber,
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun getBusinessDays(): MutableMap<DayOfWeek, Boolean> {
        val dayOfWeekMap = DayOfWeek.entries.associateWith { false }.toMutableMap()

        openDay.chunked(1).forEach { day ->
            DAY_TO_DAY_OF_WEEK_MAP[day]?.let { dayOfWeek ->
                dayOfWeekMap[dayOfWeek] = true
            }
        }

        return dayOfWeekMap
    }

    companion object {
        private val DAY_TO_DAY_OF_WEEK_MAP: Map<String, DayOfWeek> =
            DayOfWeek.values().associateBy {
                it.getDisplayName(TextStyle.SHORT, Locale.KOREA)
            }
    }
}

data class UpdateStoreRequest(
    val id: Long = 1L,
    val name: String,
    val type: String,
    val openTime: LocalTime,
    val closeTime: LocalTime,
    val openDay: String,
    val address: String,
    val phoneNumber: String,
) {
    companion object {
        fun from(store: Store): UpdateStoreRequest =
            UpdateStoreRequest(
                name = store.name,
                type = store.type.description,
                openTime = store.openTime,
                closeTime = store.closeTime,
                openDay =
                    store
                        .businessDays
                        .filterValues { it }
                        .keys
                        .joinToString("") { it.getDisplayName(TextStyle.SHORT, Locale.KOREA) },
                address = store.address,
                phoneNumber = store.phoneNumber,
            )
    }
}

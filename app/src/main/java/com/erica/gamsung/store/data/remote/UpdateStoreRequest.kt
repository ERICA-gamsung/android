package com.erica.gamsung.store.data.remote

import com.erica.gamsung.store.domain.Store
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

/**
 * {
 *     "id": 1,
 *     "name": "감성식당",
 *     "type": "음식점",
 *     "openTime": "09:00:00",
 *     "closeTime": "21:00:00",
 *     "openDay": "월화수목금",
 *     "address": "한양대학 1길",
 *     "phoneNumber": "02-1234-5678"
 * }
 */
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

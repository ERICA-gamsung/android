package com.erica.gamsung.store.data.remote

import com.erica.gamsung.store.domain.Store
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
)

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

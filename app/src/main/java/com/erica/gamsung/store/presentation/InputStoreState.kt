package com.erica.gamsung.store.presentation

import com.erica.gamsung.store.domain.StoreType
import java.time.DayOfWeek

@OptIn(ExperimentalStdlibApi::class)
data class InputStoreState(
    val name: String? = null,
    val address: String? = null,
    val phoneNumber: String? = null,
    val type: StoreType? = null,
    val businessDays: Map<DayOfWeek, Boolean> =
        DayOfWeek.entries.associateWith { false },
)

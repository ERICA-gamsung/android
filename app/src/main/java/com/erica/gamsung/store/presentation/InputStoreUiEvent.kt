package com.erica.gamsung.store.presentation

import com.erica.gamsung.store.domain.StoreType
import java.time.DayOfWeek

sealed interface InputStoreUiEvent {
    data class NameChanged(
        val name: String,
    ) : InputStoreUiEvent

    data class AddressChanged(
        val address: String,
    ) : InputStoreUiEvent

    data class PhoneNumberChanged(
        val phoneNumber: String,
    ) : InputStoreUiEvent

    data class TypeChanged(
        val type: StoreType,
    ) : InputStoreUiEvent

    data class BusinessDaysChanged(
        val day: DayOfWeek,
    ) : InputStoreUiEvent
}

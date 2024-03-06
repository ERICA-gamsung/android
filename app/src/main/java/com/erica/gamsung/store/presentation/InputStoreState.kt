package com.erica.gamsung.store.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.erica.gamsung.store.domain.Store
import com.erica.gamsung.store.domain.StoreType
import com.erica.gamsung.store.presentation.utils.toLocalTime
import java.time.DayOfWeek

@OptIn(ExperimentalStdlibApi::class, ExperimentalMaterial3Api::class)
data class InputStoreState(
    val name: String? = null,
    val address: String? = null,
    val phoneNumber: String? = null,
    val type: StoreType? = null,
    val businessDays: Map<DayOfWeek, Boolean> =
        DayOfWeek.entries.associateWith { false },
    val openTime: TimePickerState? = null,
    val closeTime: TimePickerState? = null,
) {
    fun toDomainModel(): Store =
        Store(
            name = name!!,
            address = address!!,
            phoneNumber = phoneNumber!!,
            type = type!!,
            businessDays = businessDays,
            openTime = openTime!!.toLocalTime(),
            closeTime = closeTime!!.toLocalTime(),
        )

    private val isNameValid: Boolean
        get() = name?.isNotBlank() ?: false

    private val isAddressValid: Boolean
        get() = address?.isNotBlank() ?: false

    private val isPhoneNumberValid: Boolean
        get() = phoneNumber?.matches(Regex("^0(2|[0-9]{2})-(\\d{3,4})-(\\d{4})$")) ?: false

    private val isTypeValid: Boolean
        get() = type != null

    private val isBusinessDaysValid: Boolean
        get() = businessDays.containsValue(true)

    private val isOpenTimeValid: Boolean
        get() = openTime != null

    private val isCloseTimeValid: Boolean
        get() = closeTime != null

    val isValid: Boolean
        get() =
            isNameValid and isAddressValid and isPhoneNumberValid and
                isTypeValid and isBusinessDaysValid and isOpenTimeValid and isCloseTimeValid
}

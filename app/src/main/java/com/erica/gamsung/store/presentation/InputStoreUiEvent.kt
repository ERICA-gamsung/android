package com.erica.gamsung.store.presentation

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
}

package com.erica.gamsung.store.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StoreViewModel : ViewModel() {
    private var _inputStoreState = MutableStateFlow(InputStoreState())
    val inputStoreState = _inputStoreState.asStateFlow()

    fun onEvent(event: InputStoreUiEvent) {
        when (event) {
            is InputStoreUiEvent.NameChanged -> handleNameChanged(event)
            is InputStoreUiEvent.AddressChanged -> handleAddressChanged(event)
            is InputStoreUiEvent.PhoneNumberChanged -> handlePhoneNumberChanged(event)
            is InputStoreUiEvent.TypeChanged -> handleTypeChanged(event)
            is InputStoreUiEvent.BusinessDaysChanged -> handleBusinessDaysChanged(event)
        }
    }

    private fun handleNameChanged(event: InputStoreUiEvent.NameChanged) {
        _inputStoreState.update {
            it.copy(
                name = event.name,
            )
        }
    }

    private fun handleAddressChanged(event: InputStoreUiEvent.AddressChanged) {
        _inputStoreState.update {
            it.copy(
                address = event.address,
            )
        }
    }

    private fun handlePhoneNumberChanged(event: InputStoreUiEvent.PhoneNumberChanged) {
        _inputStoreState.update {
            it.copy(
                phoneNumber = event.phoneNumber,
            )
        }
    }

    private fun handleTypeChanged(event: InputStoreUiEvent.TypeChanged) {
        _inputStoreState.update {
            it.copy(
                type = event.type,
            )
        }
    }

    private fun handleBusinessDaysChanged(event: InputStoreUiEvent.BusinessDaysChanged) {
        _inputStoreState.update {
            it.copy(
                businessDays =
                    it.businessDays.toMutableMap().apply {
                        this[event.day] = !(this[event.day] ?: false)
                    },
            )
        }
    }
}

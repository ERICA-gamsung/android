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
}

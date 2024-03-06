package com.erica.gamsung.store.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import com.erica.gamsung.store.domain.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class StoreViewModel
    @Inject
    constructor(
        private val storeRepository: StoreRepository,
    ) : ViewModel() {
        private var _inputStoreState = MutableStateFlow(InputStoreState())
        val inputStoreState = _inputStoreState.asStateFlow()

        fun onEvent(event: InputStoreUiEvent) {
            when (event) {
                is InputStoreUiEvent.NameChanged -> handleNameChanged(event)
                is InputStoreUiEvent.AddressChanged -> handleAddressChanged(event)
                is InputStoreUiEvent.PhoneNumberChanged -> handlePhoneNumberChanged(event)
                is InputStoreUiEvent.TypeChanged -> handleTypeChanged(event)
                is InputStoreUiEvent.BusinessDaysChanged -> handleBusinessDaysChanged(event)
                is InputStoreUiEvent.OpenTimeUpdate -> handleOpenTimeUpdate(event)
                is InputStoreUiEvent.CloseTimeUpdate -> handleCloseTimeUpdate(event)
                InputStoreUiEvent.SendStore -> handleSendStore()
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

        private fun handleOpenTimeUpdate(event: InputStoreUiEvent.OpenTimeUpdate) {
            _inputStoreState.update {
                it.copy(
                    openTime = event.timePickerState,
                )
            }
        }

        private fun handleCloseTimeUpdate(event: InputStoreUiEvent.CloseTimeUpdate) {
            _inputStoreState.update {
                it.copy(
                    closeTime = event.timePickerState,
                )
            }
        }

        private fun handleSendStore() {
            CoroutineScope(Dispatchers.IO).launch {
                storeRepository.updateStore(inputStoreState.value.toDomainModel())
            }
        }
    }

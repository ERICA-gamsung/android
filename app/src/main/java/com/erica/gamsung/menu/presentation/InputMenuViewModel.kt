package com.erica.gamsung.menu.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InputMenuViewModel : ViewModel() {
    private var _inputMenuState = MutableStateFlow(InputMenuState())
    val inputMenuState = _inputMenuState.asStateFlow()

    fun onEvent(event: InputMenuUiEvent) {
        when (event) {
            is InputMenuUiEvent.NameChanged -> {
                _inputMenuState.update {
                    it.copy(
                        name = event.name,
                    )
                }
            }
        }
    }
}

sealed interface InputMenuUiEvent {
    data class NameChanged(
        val name: String,
    ) : InputMenuUiEvent
}

data class InputMenuState(
    val name: String = "",
)

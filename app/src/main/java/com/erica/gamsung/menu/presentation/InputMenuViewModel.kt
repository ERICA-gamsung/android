package com.erica.gamsung.menu.presentation

import androidx.lifecycle.ViewModel
import com.erica.gamsung.menu.domain.Menu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InputMenuViewModel(
    initialMenus: List<Menu> = emptyList(),
    initialInputMenuState: InputMenuState = InputMenuState(),
) : ViewModel() {
    private var _menusState = MutableStateFlow(initialMenus)
    val menusState = _menusState.asStateFlow()

    private var _inputMenuState = MutableStateFlow(initialInputMenuState)
    val inputMenuState = _inputMenuState.asStateFlow()

    private var _shouldNavigateState = MutableStateFlow(false)
    val shouldNavigateState = _shouldNavigateState.asStateFlow()

    fun onEvent(event: InputMenuUiEvent) {
        when (event) {
            is InputMenuUiEvent.NameChanged -> handleNameChanged(event)
            is InputMenuUiEvent.PriceChanged -> handlePriceChanged(event)
            InputMenuUiEvent.AddMenu -> handleAddMenu()
            is InputMenuUiEvent.RemoveMenu -> handleRemoveMenu(event)
            InputMenuUiEvent.SendMenus -> handleSendMenus()
        }
    }

    private fun handleNameChanged(event: InputMenuUiEvent.NameChanged) {
        _inputMenuState.update {
            it.copy(
                name = event.name,
            )
        }
    }

    private fun handlePriceChanged(event: InputMenuUiEvent.PriceChanged) {
        _inputMenuState.update {
            it.copy(
                price = event.price,
            )
        }
    }

    private fun handleAddMenu() {
        val isNameValid = _inputMenuState.value.name.isNotBlank()
        val isPriceValid = _inputMenuState.value.price.isZeroOrPrimitiveInt()

        if (isNameValid && isPriceValid) {
            _menusState.update {
                it.plusElement(Menu(_inputMenuState.value.name, _inputMenuState.value.price.toInt()))
            }

            _inputMenuState.update {
                it.copy(
                    name = "",
                    price = "",
                    isNameValid = true,
                    isPriceValid = true,
                )
            }
        } else {
            _inputMenuState.update {
                it.copy(
                    isNameValid = isNameValid,
                    isPriceValid = isPriceValid,
                )
            }
        }
    }

    private fun handleRemoveMenu(event: InputMenuUiEvent.RemoveMenu) {
        _menusState.update {
            it.filterIndexed { index, _ -> index != event.index }
        }
    }

    private fun handleSendMenus() {
        val menus = _menusState.value

        if (menus.isEmpty()) {
            _inputMenuState.update {
                it.copy(
                    isNameValid = false,
                    isPriceValid = false,
                )
            }
        } else {
            // TODO 서버로 메뉴 전송
            _shouldNavigateState.value = true
        }
    }

    private fun String.isZeroOrPrimitiveInt(): Boolean {
        val int = this.toIntOrNull() ?: return false
        return int >= 0
    }
}

sealed interface InputMenuUiEvent {
    data class NameChanged(
        val name: String,
    ) : InputMenuUiEvent

    data class PriceChanged(
        val price: String,
    ) : InputMenuUiEvent

    data object AddMenu : InputMenuUiEvent

    data class RemoveMenu(
        val index: Int,
    ) : InputMenuUiEvent

    data object SendMenus : InputMenuUiEvent
}

data class InputMenuState(
    val name: String = "",
    val price: String = "",
    val isNameValid: Boolean = true,
    val isPriceValid: Boolean = true,
)

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

    fun onEvent(event: InputMenuUiEvent) {
        when (event) {
            is InputMenuUiEvent.NameChanged -> {
                _inputMenuState.update {
                    it.copy(
                        name = event.name,
                    )
                }
            }

            is InputMenuUiEvent.PriceChanged -> {
                _inputMenuState.update {
                    it.copy(
                        price = event.price,
                    )
                }
            }

            InputMenuUiEvent.AddMenu -> {
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

            is InputMenuUiEvent.RemoveMenu -> {
                _menusState.update {
                    it.filterIndexed { index, _ -> index != event.index }
                }
            }
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
}

data class InputMenuState(
    val name: String = "",
    val price: String = "",
    val isNameValid: Boolean = true,
    val isPriceValid: Boolean = true,
)

package com.erica.gamsung.menu.presentation

import androidx.lifecycle.ViewModel
import com.erica.gamsung.menu.domain.Menu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InputMenuViewModel(
    initialMenus: List<Menu> = emptyList(),
) : ViewModel() {
    private var _menusState = MutableStateFlow(initialMenus)
    val menusState = _menusState.asStateFlow()

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

            is InputMenuUiEvent.PriceChanged -> {
                _inputMenuState.update {
                    it.copy(
                        price = event.price,
                    )
                }
            }

            is InputMenuUiEvent.RemoveMenu -> {
                _menusState.update {
                    it.filterIndexed { index, _ -> index != event.index }
                }
            }
        }
    }
}

sealed interface InputMenuUiEvent {
    data class NameChanged(
        val name: String,
    ) : InputMenuUiEvent

    data class PriceChanged(
        val price: String,
    ) : InputMenuUiEvent

    data class RemoveMenu(
        val index: Int,
    ) : InputMenuUiEvent
}

data class InputMenuState(
    val name: String = "",
    val price: String = "",
)

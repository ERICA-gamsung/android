package com.erica.gamsung.menu.presentation

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

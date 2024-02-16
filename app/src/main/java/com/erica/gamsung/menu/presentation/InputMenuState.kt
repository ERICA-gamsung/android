package com.erica.gamsung.menu.presentation

data class InputMenuState(
    val name: String = "",
    val price: String = "",
    val isNameValid: Boolean = true,
    val isPriceValid: Boolean = true,
)

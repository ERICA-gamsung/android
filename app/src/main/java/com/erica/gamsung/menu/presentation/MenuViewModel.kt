package com.erica.gamsung.menu.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erica.gamsung.login.domain.LoginRepository
import com.erica.gamsung.menu.data.repository.FakeMenuRepositoryImpl
import com.erica.gamsung.menu.domain.Menu
import com.erica.gamsung.menu.domain.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel
    @Inject
    constructor(
        state: SavedStateHandle,
        private val menuRepository: MenuRepository = FakeMenuRepositoryImpl(),
        private val loginRepository: LoginRepository,
    ) : ViewModel() {
        private var _menusState = MutableStateFlow(state.get<List<Menu>>(MENUS) ?: emptyList())
        val menusState = _menusState.asStateFlow()

        private var _inputMenuState = MutableStateFlow(state.get<InputMenuState>(INPUT_MENU) ?: InputMenuState())
        val inputMenuState = _inputMenuState.asStateFlow()

        val isValid: Boolean get() = _menusState.value.isNotEmpty()

        init {
            loadMenus()
        }

        private fun loadMenus() {
            viewModelScope.launch(Dispatchers.IO) {
                menuRepository.getMenus(loginRepository.getMemberId()).collect { menus ->
                    _menusState.update { menus }
                }
            }
        }

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
                CoroutineScope(Dispatchers.IO).launch {
                    menuRepository.updateMenus(menus, loginRepository.getMemberId())
                }
            }
        }

        private fun String.isZeroOrPrimitiveInt(): Boolean {
            val int = this.toIntOrNull() ?: return false
            return int >= 0
        }

        companion object {
            const val MENUS = "menus"
            const val INPUT_MENU = "inputMenu"
        }
    }

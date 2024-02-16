package com.erica.gamsung.menu.presentation

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class InputMenuViewModelTest :
    FunSpec({

        test("NameChanged 이벤트로 추가할 메뉴명을 수정할 수 있다.") {
            // Given
            val viewModel = InputMenuViewModel()

            // When
            viewModel.onEvent(InputMenuUiEvent.NameChanged("새 메뉴"))

            // Then
            val name = viewModel.inputMenuState.value.name
            name shouldBe "새 메뉴"
        }
    })

package com.erica.gamsung.menu.presentation

import androidx.lifecycle.SavedStateHandle
import com.erica.gamsung.menu.domain.Menu
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class InputMenuViewModelTest :
    FunSpec({
        context("NameChanged 이벤트를 발생시켰을 때") {
            test("추가할 메뉴명을 수정할 수 있다.") {
                // Given
                val viewModel = InputMenuViewModel(SavedStateHandle())

                // When
                viewModel.onEvent(InputMenuUiEvent.NameChanged("새 메뉴"))

                // Then
                val name = viewModel.inputMenuState.value.name
                name shouldBe "새 메뉴"
            }
        }

        context("PriceChanged 이벤트를 발생시켰을 때") {
            test("추가할 메뉴명을 수정할 수 있다.") {
                // Given
                val viewModel = InputMenuViewModel(SavedStateHandle())

                // When
                viewModel.onEvent(InputMenuUiEvent.PriceChanged("3000"))

                // Then
                val price = viewModel.inputMenuState.value.price
                price shouldBe "3000"
            }
        }

        context("AddMenu 이벤트를 발생시켰을 때") {
            test("메뉴를 추가할 수 있다.") {
                // Given
                val initialInputMenuState = InputMenuState("새 메뉴", "3000")
                val initialState = mapOf(InputMenuViewModel.INPUT_MENU to initialInputMenuState)
                val viewModel = InputMenuViewModel(SavedStateHandle(initialState))

                // When
                viewModel.onEvent(InputMenuUiEvent.AddMenu)

                // Then
                val menus = viewModel.menusState.value
                menus shouldHaveSize 1
                menus shouldContain Menu("새 메뉴", 3000)
            }

            test("메뉴명이 공백이거나 비어있으면 메뉴를 추가할 수 없다") {
                // Given
                val initialInputMenuState = InputMenuState(" ", "3000")
                val initialState = mapOf(InputMenuViewModel.INPUT_MENU to initialInputMenuState)
                val viewModel = InputMenuViewModel(SavedStateHandle(initialState))

                // When
                viewModel.onEvent(InputMenuUiEvent.AddMenu)

                // Then
                val menus = viewModel.menusState.value
                menus shouldHaveSize 0
            }

            test("가격이 숫자가 아니면 메뉴를 추가할 수 없다") {
                // Given
                val initialInputMenuState = InputMenuState("새 메뉴", "삼천원")
                val initialState = mapOf(InputMenuViewModel.INPUT_MENU to initialInputMenuState)
                val viewModel = InputMenuViewModel(SavedStateHandle(initialState))

                // When
                viewModel.onEvent(InputMenuUiEvent.AddMenu)

                // Then
                val menus = viewModel.menusState.value
                menus shouldHaveSize 0
            }

            test("가격이 음수면 메뉴를 추가할 수 없다") {
                // Given
                val initialInputMenuState = InputMenuState("새 메뉴", "-3000")
                val initialState = mapOf(InputMenuViewModel.INPUT_MENU to initialInputMenuState)
                val viewModel = InputMenuViewModel(SavedStateHandle(initialState))

                // When
                viewModel.onEvent(InputMenuUiEvent.AddMenu)

                // Then
                val menus = viewModel.menusState.value
                menus shouldHaveSize 0
            }

            test("메뉴 추가 후에 추가할 메뉴명과 추가할 가격은 빈 문자열로 초기화된다.") {
                // Given
                val initialInputMenuState = InputMenuState("새 메뉴", "3000")
                val initialState = mapOf(InputMenuViewModel.INPUT_MENU to initialInputMenuState)
                val viewModel = InputMenuViewModel(SavedStateHandle(initialState))

                // When
                viewModel.onEvent(InputMenuUiEvent.AddMenu)

                // Then
                val name = viewModel.inputMenuState.value.name
                val price = viewModel.inputMenuState.value.price
                name shouldBe ""
                price shouldBe ""
            }
        }

        context("RemoveMenu 이벤트를 발생시켰을 때") {
            test("전달된 index의 메뉴를 제거할 수 있다.") {
                // Given
                val initialMenus =
                    listOf(
                        Menu("메뉴1", 1000),
                        Menu("메뉴2", 2000),
                        Menu("메뉴3", 3000),
                    )
                val initialState = mapOf(InputMenuViewModel.MENUS to initialMenus)
                val viewModel = InputMenuViewModel(SavedStateHandle(initialState))

                // When
                viewModel.onEvent(InputMenuUiEvent.RemoveMenu(1))

                // Then
                val menus = viewModel.menusState.value
                menus shouldHaveSize 2
                menus shouldContainInOrder
                    listOf(
                        Menu("메뉴1", 1000),
                        Menu("메뉴3", 3000),
                    )
            }
        }

        context("SendEvent 이벤트를 발생시켰을 때") {
            test("입력된 메뉴가 하나도 없을 시 메인 페이지로 이동하면 안된다.") {
                // Given
                val initialMenus = emptyList<Menu>()
                val initialState = mapOf(InputMenuViewModel.MENUS to initialMenus)
                val viewModel = InputMenuViewModel(SavedStateHandle(initialState))

                // When
                viewModel.onEvent(InputMenuUiEvent.SendMenus)

                // Then
                val shouldNavigate = viewModel.shouldNavigateState.value
                shouldNavigate shouldBe false
            }

            test("입력된 메뉴가 하나도 없을 시 메뉴와 가격을 입력하라고 표시된다.") {
                // Given
                val initialMenus = emptyList<Menu>()
                val initialState = mapOf(InputMenuViewModel.MENUS to initialMenus)
                val viewModel = InputMenuViewModel(SavedStateHandle(initialState))

                // When
                viewModel.onEvent(InputMenuUiEvent.SendMenus)

                // Then
                val isNameValid = viewModel.inputMenuState.value.isNameValid
                val isPriceValid = viewModel.inputMenuState.value.isPriceValid
                isNameValid shouldBe false
                isPriceValid shouldBe false
            }

            test("입력된 메뉴가 한 개 이상이면 메인 페이지로 이동해도 된다.") {
                // Given
                val initialMenus = listOf(Menu("입력된 메뉴", 1000))
                val initialState = mapOf(InputMenuViewModel.MENUS to initialMenus)
                val viewModel = InputMenuViewModel(SavedStateHandle(initialState))

                // When
                viewModel.onEvent(InputMenuUiEvent.SendMenus)

                // Then
                val shouldNavigate = viewModel.shouldNavigateState.value
                shouldNavigate shouldBe true
            }
        }
    })

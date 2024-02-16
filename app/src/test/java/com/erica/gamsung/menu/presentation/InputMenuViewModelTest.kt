package com.erica.gamsung.menu.presentation

import com.erica.gamsung.menu.domain.Menu
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
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

        test("PriceChanged 이벤트로 추가할 메뉴명을 수정할 수 있다.") {
            // Given
            val viewModel = InputMenuViewModel()

            // When
            viewModel.onEvent(InputMenuUiEvent.PriceChanged("3000"))

            // Then
            val price = viewModel.inputMenuState.value.price
            price shouldBe "3000"
        }

        test("AddMenu 이벤트로 메뉴를 추가할 수 있다.") {
            // Given
            val viewModel = InputMenuViewModel(initialInputMenuState = InputMenuState("새 메뉴", "3000"))

            // When
            viewModel.onEvent(InputMenuUiEvent.AddMenu)

            // Then
            val menus = viewModel.menusState.value
            menus shouldHaveSize 1
            menus shouldContain Menu("새 메뉴", 3000)
        }

        test("메뉴명이 공백이거나 비어있으면 AddMenu 이벤트로 메뉴를 추가할 수 없다") {
            // Given
            val viewModel = InputMenuViewModel(initialInputMenuState = InputMenuState(" ", "3000"))

            // When
            viewModel.onEvent(InputMenuUiEvent.AddMenu)

            // Then
            val menus = viewModel.menusState.value
            menus shouldHaveSize 0
        }

        test("가격이 숫자가 아니면 AddMenu 이벤트로 메뉴를 추가할 수 없다") {
            // Given
            val viewModel = InputMenuViewModel(initialInputMenuState = InputMenuState("새 메뉴", "삼천원"))

            // When
            viewModel.onEvent(InputMenuUiEvent.AddMenu)

            // Then
            val menus = viewModel.menusState.value
            menus shouldHaveSize 0
        }

        test("가격이 음수면 AddMenu 이벤트로 메뉴를 추가할 수 없다") {
            // Given
            val viewModel = InputMenuViewModel(initialInputMenuState = InputMenuState("새 메뉴", "-3000"))

            // When
            viewModel.onEvent(InputMenuUiEvent.AddMenu)

            // Then
            val menus = viewModel.menusState.value
            menus shouldHaveSize 0
        }

        test("AddMenu 이벤트로 메뉴 추가 후에 추가할 메뉴명과 추가할 가격은 빈 문자열로 초기화된다.") {
            // Given
            val viewModel = InputMenuViewModel(initialInputMenuState = InputMenuState("새 메뉴", "3000"))

            // When
            viewModel.onEvent(InputMenuUiEvent.AddMenu)

            // Then
            val name = viewModel.inputMenuState.value.name
            val price = viewModel.inputMenuState.value.price
            name shouldBe ""
            price shouldBe ""
        }

        test("RemoveMenu 이벤트로 메뉴를 제거할 수 있다.") {
            // Given
            val viewModel =
                InputMenuViewModel(
                    initialMenus =
                        listOf(
                            Menu("메뉴1", 1000),
                            Menu("메뉴2", 2000),
                            Menu("메뉴3", 3000),
                        ),
                )

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
    })

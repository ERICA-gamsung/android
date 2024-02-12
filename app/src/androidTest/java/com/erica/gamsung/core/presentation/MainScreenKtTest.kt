package com.erica.gamsung.core.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenKtTest {
    @get:Rule
    val rule: ComposeContentTestRule = createComposeRule()

    @Before
    fun setUp() {
        rule.setContent {
            MainScreen()
        }
    }

    @Test
    fun 메인페이지에_탑바가_화면에_보인다() {
        rule
            .onNodeWithText("메인 페이지")
            .assertIsDisplayed()
    }

    @Test
    fun 메인페이지에_글_발행_버튼이_화면에_보인다() {
        rule
            .onNodeWithText("글 발행하러 가기")
            .assertIsDisplayed()
    }

    @Test
    fun 메인페이지에_발행_현황_확인_버튼이_화면에_보인다() {
        rule
            .onNodeWithText("발행 현황 확인하기")
            .assertIsDisplayed()
    }
}

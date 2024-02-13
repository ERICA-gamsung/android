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

    /** 메인 페이지에 탑바가 화면에 보인다 */
    @Test
    fun topBarIsDisplayedOnMainPage() {
        rule
            .onNodeWithText("메인 페이지")
            .assertIsDisplayed()
    }

    /** 메인 페이지에 글 발행 버튼이 화면에 보인다 */
    @Test
    fun publishButtonIsDisplayedOnMainPage() {
        rule
            .onNodeWithText("글 발행하러 가기")
            .assertIsDisplayed()
    }

    /** 메인 페이지에 발행 현황 확인 버튼이 화면에 보인다 */
    @Test
    fun checkPublishStatusButtonIsDisplayedOnMainPage() {
        rule
            .onNodeWithText("발행 현황 확인하기")
            .assertIsDisplayed()
    }
}

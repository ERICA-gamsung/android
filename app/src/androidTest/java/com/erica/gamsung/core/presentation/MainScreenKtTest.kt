package com.erica.gamsung.core.presentation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
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
    lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        rule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MainNavHost(navController = navController)
        }
    }

    /** 메인 페이지에 탑바가 화면에 보인다 */
    @Test
    fun topBarIsDisplayedOnMainPage() {
        rule
            .onNodeWithText("메인 페이지")
            .assertIsDisplayed()
    }

    /** 설정 버튼을 누르면 설정 페이지로 이동한다 */
    @Test
    fun navigateToSettingPageWhenSettingButtonIsClicked() {
        rule
            .onNodeWithContentDescription("AccountCircle")
            .performClick()

        val route = navController.currentDestination?.route
        assertEquals(route, Screen.SETTING.route)
    }

    /** 메인 페이지에 글 발행 버튼이 화면에 보인다 */
    @Test
    fun publishButtonIsDisplayedOnMainPage() {
        rule
            .onNodeWithText("글 발행하러 가기")
            .assertIsDisplayed()
    }

    /** 글 발행 버튼을 누르면 글 발행 페이지로 이동한다 */
    @Test
    fun navigateToPublishPostingPageWhenPublishPostingButtonIsClicked() {
        rule
            .onNodeWithText("글 발행하러 가기")
            .performClick()

        val route = navController.currentDestination?.route
        assertEquals(route, Screen.PUBLISH_POSTING.route)
    }

    /** 메인 페이지에 발행 현황 확인 버튼이 화면에 보인다 */
    @Test
    fun checkPublishStatusButtonIsDisplayedOnMainPage() {
        rule
            .onNodeWithText("발행 현황 확인하기")
            .assertIsDisplayed()
    }

    /** 발행 현황 확인 버튼을 누르면 발행 현황 페이지로 이동한다 */
    @Test
    fun navigateToCheckPostingPageWhenCheckPostingButtonIsClicked() {
        rule
            .onNodeWithText("발행 현황 확인하기")
            .performClick()

        val route = navController.currentDestination?.route
        assertEquals(route, Screen.CHECK_POSTING.route)
    }
}

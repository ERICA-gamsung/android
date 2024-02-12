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

    @Test
    fun 설정_버튼을_누르면_설정_페이지로_이동한다() {
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


    @Test
    fun 글_발행_버튼을_누르면_글_발행_페이지로_이동한다() {
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

    @Test
    fun 발행_현황_확인_버튼을_누르면_발행_현황_페이지로_이동한다() {
        rule
            .onNodeWithText("발행 현황 확인하기")
            .performClick()

        val route = navController.currentDestination?.route
        assertEquals(route, Screen.CHECK_POSTING.route)
    }
}

package com.erica.gamsung.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.core.presentation.theme.GamsungTheme
import com.erica.gamsung.login.presentation.LoginScreen
import com.erica.gamsung.menu.presentation.InputMenuScreen
import com.erica.gamsung.setting.presentation.SettingScreen
import com.erica.gamsung.store.presentation.InputStoreScreen
import com.erica.gamsung.uploadTime.presentation.CalendarViewModel
import com.erica.gamsung.uploadTime.presentation.MyCalendarScreen
import com.erica.gamsung.uploadTime.presentation.MyScheduleScreen
import com.erica.gamsung.uploadTime.presentation.ScheduleListScreen
import dagger.hilt.android.AndroidEntryPoint

// 동일한 viewmodel을 2개의 page가 공유하기 위해서는 hilt를 이용한 DI가 필요하다고 한다.
// 일단은 상위 컴포넌트에서 생성해서 사용.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GamsungTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    // calendarViewModel 여기서 생성
                    val calendarViewModel: CalendarViewModel = viewModel()
                    MainNavHost(
                        navController = navController,
                        calendarViewModel = calendarViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    calendarViewModel: CalendarViewModel,
) {
    NavHost(navController = navController, startDestination = Screen.MAIN.route) {
        composable(Screen.MAIN.route) { MainScreen(navController = navController) }
        composable(Screen.SETTING.route) { SettingScreen(navController = navController) }
        composable(Screen.PUBLISH_POSTING.route) { PublishPostingScreen() }
        composable(Screen.CHECK_POSTING.route) { CheckPostingScreen() }
        composable(Screen.INPUT_STORE.route) { InputStoreScreen(navController = navController) }
        composable(Screen.INPUT_MENU.route) { InputMenuScreen(navController = navController) }
        composable(Screen.DATE_SELECT.route) {
            MyCalendarScreen(navController = navController, viewModel = calendarViewModel)
        }
        composable(Screen.TIME_SELECT.route) {
            MyScheduleScreen(navController = navController, viewModel = calendarViewModel)
        }
        composable(Screen.DATE_TIME_LIST_CHECK.route) {
            ScheduleListScreen(navController = navController, viewModel = calendarViewModel)
        }
        composable(Screen.LOGIN.route) { LoginScreen(navController = navController) }
    }
}

@Composable
fun PublishPostingScreen() {
    Text(text = "PublishPostingScreen") // TODO 구현 시작 시 제거 및 코드 이동
}

@Composable
fun CheckPostingScreen() {
    Text(text = "CheckPostingScreen") // TODO 구현 시작 시 제거 및 코드 이동
}

package com.erica.gamsung.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.core.presentation.theme.GamsungTheme
import com.erica.gamsung.login.presentation.LoginScreen
import com.erica.gamsung.menu.presentation.InputMenuScreen
import com.erica.gamsung.post.presentation.PostStatusScreen
import com.erica.gamsung.post.presentation.PostViewModel
import com.erica.gamsung.post.presentation.PreviewPost
import com.erica.gamsung.post.presentation.SelectPostScreen
import com.erica.gamsung.setting.presentation.SettingScreen
import com.erica.gamsung.store.presentation.InputStoreScreen
import com.erica.gamsung.uploadTime.presentation.MyCalendarScreen
import com.erica.gamsung.uploadTime.presentation.MyScheduleScreen
import com.erica.gamsung.uploadTime.presentation.ScheduleListScreen
import com.erica.gamsung.uploadTime.presentation.ScheduleViewModel
import com.erica.gamsung.uploadTime.presentation.UploadTimeConfirmScreen
import dagger.hilt.android.AndroidEntryPoint

// 동일한 viewmodel을 2개의 page가 공유하기 위해서는 hilt를 이용한 DI가 필요하다고 한다.
// 일단은 상위 컴포넌트에서 생성해서 사용.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        val scheduleViewModel: ScheduleViewModel by viewModels()
        setContent {
            GamsungTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    MainNavHost(
                        navController = navController,
                        scheduleViewModel = scheduleViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    scheduleViewModel: ScheduleViewModel,
) {
    val postViewModel: PostViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) { MainScreen(navController = navController) }
        composable(Screen.Setting.route) { SettingScreen(navController = navController) }
        composable(Screen.PublishPosting.route) { PublishPostingScreen() }
        composable(Screen.CheckPosting.route) { CheckPostingScreen() }
        composable(Screen.InputStore(isEditMode = true).route) {
            InputStoreScreen(navController = navController, isEditMode = true)
        }
        composable(Screen.InputStore(isEditMode = false).route) {
            InputStoreScreen(navController = navController, isEditMode = false)
        }
        composable(Screen.InputMenu(isEditMode = true).route) {
            InputMenuScreen(navController = navController, isEditMode = true)
        }
        composable(Screen.InputMenu(isEditMode = false).route) {
            InputMenuScreen(navController = navController, isEditMode = false)
        }
        composable(Screen.DateSelect.route) {
            MyCalendarScreen(navController = navController, viewModel = scheduleViewModel)
        }
        composable(Screen.TimeSelect.route) {
            MyScheduleScreen(navController = navController, viewModel = scheduleViewModel)
        }
        composable(Screen.DateTimeListCheck.route) {
            ScheduleListScreen(navController = navController, viewModel = scheduleViewModel)
        }
        composable(Screen.DateTimeFinish.route) {
            UploadTimeConfirmScreen(navController = navController)
        }
        composable(Screen.Login.route) { LoginScreen(navController = navController) }
        composable(Screen.SelectNewPost.route) {
            SelectPostScreen(navController = navController, postViewModel)
        }
        composable(Screen.PreviewNewPost.route) {
            PreviewPost(navController = navController)
        }
        composable(Screen.PostsStatus.route) {
            PostStatusScreen(navController = navController, postViewModel)
        }
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

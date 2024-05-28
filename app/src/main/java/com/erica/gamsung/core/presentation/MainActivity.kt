package com.erica.gamsung.core.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.core.presentation.theme.GamsungTheme
import com.erica.gamsung.login.presentation.LoginScreen
import com.erica.gamsung.login.presentation.LoginViewModel
import com.erica.gamsung.menu.presentation.InputMenuScreen
import com.erica.gamsung.menu.presentation.MenuViewModel
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
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var navController: NavHostController

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
                    navController = rememberNavController()
                    MainNavHost(
                        navController = navController,
                        scheduleViewModel = scheduleViewModel,
                        startScreen = if (loginViewModel.isLogin()) Screen.Main else Screen.Login,
                    )
                }
            }
        }

        handleDeepLink(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        intent?.data?.let { uri ->
            if (uri.toString().startsWith("https://gamsung.shop/android")) {
                loginViewModel.fetchAccessToken { hasAccount ->
                    if (hasAccount) {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.InputStore(isEditMode = false).route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    scheduleViewModel: ScheduleViewModel,
    startScreen: Screen,
) {
    val postViewModel: PostViewModel = hiltViewModel()
    val menuViewModel: MenuViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = startScreen.route) {
        composable(Screen.Main.route) {
            MainScreen(navController = navController, postViewModel)
            LogNavStack(navController = navController)
        }
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
            InputMenuScreen(navController = navController, isEditMode = true, menuViewModel = menuViewModel)
        }
        composable(Screen.InputMenu(isEditMode = false).route) {
            InputMenuScreen(navController = navController, isEditMode = false, menuViewModel = menuViewModel)
        }
        composable(Screen.DateSelect.route) {
            MyCalendarScreen(navController = navController, viewModel = scheduleViewModel)
            LogNavStack(navController = navController)
        }
        composable(Screen.TimeSelect.route) {
            MyScheduleScreen(
                navController = navController,
                viewModel = scheduleViewModel,
                menuViewModel = menuViewModel,
            )
            LogNavStack(navController = navController)
        }
        composable(Screen.DateTimeListCheck.route) {
            ScheduleListScreen(navController = navController, viewModel = scheduleViewModel)
            LogNavStack(navController = navController)
        }
        composable(Screen.DateTimeFinish.route) {
            UploadTimeConfirmScreen(navController = navController)
            LogNavStack(navController = navController)
        }
        composable(Screen.Login.route) { LoginScreen() }
        composable(Screen.SelectNewPost.route) {
            SelectPostScreen(navController = navController, postViewModel)
            LogNavStack(navController = navController)
        }
        composable(Screen.PreviewNewPost.route) {
            PreviewPost(navController = navController, postViewModel)
            LogNavStack(navController = navController)
        }
        composable(Screen.PostsStatus.route) {
            PostStatusScreen(navController = navController, postViewModel)
            LogNavStack(navController = navController)
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

@Composable
fun LogNavStack(navController: NavHostController) {
    LaunchedEffect(navController) {
        navController.currentBackStackEntry?.let { entry ->
            Log.d("NavStack", "Current Destination: ${entry.destination.route}")
        }
        navController.previousBackStackEntry?.let { entry ->
            Log.d("NavStack", "Previous Destination: ${entry.destination.route}")
        }
    }
}

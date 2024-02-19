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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.menu.presentation.InputMenuScreen
import com.erica.gamsung.menu.presentation.InputMenuViewModel
import com.erica.gamsung.ui.theme.GamsungTheme

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

                    MainNavHost(navController)
                }
            }
        }
    }
}

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.MAIN.route) {
        composable(Screen.MAIN.route) { MainScreen(navController = navController) }
        composable(Screen.SETTING.route) { SettingScreen() }
        composable(Screen.PUBLISH_POSTING.route) { PublishPostingScreen() }
        composable(Screen.CHECK_POSTING.route) { CheckPostingScreen() }
        composable(Screen.INPUT_MENU.route) {
            InputMenuScreen(navController = navController, inputMenuViewModel = InputMenuViewModel())
        }
    }
}

@Composable
fun SettingScreen() {
    Text(text = "SettingScreen") // TODO 구현 시작 시 제거 및 코드 이동
}

@Composable
fun PublishPostingScreen() {
    Text(text = "PublishPostingScreen") // TODO 구현 시작 시 제거 및 코드 이동
}

@Composable
fun CheckPostingScreen() {
    Text(text = "CheckPostingScreen") // TODO 구현 시작 시 제거 및 코드 이동
}

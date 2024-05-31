package com.erica.gamsung.setting.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.core.presentation.Screen
import com.erica.gamsung.core.presentation.component.GsTextButtonWithIcon
import com.erica.gamsung.core.presentation.component.GsTopAppBar
import com.erica.gamsung.login.presentation.LoginViewModel

private val ButtonHeight = 70.dp

@Composable
fun SettingScreen(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = { GsTopAppBar(title = "마이페이지") },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                StoreSection(
                    modifier = Modifier.weight(2f),
                    onNavigate = { screen ->
                        navController.navigate(screen.route)
                    },
                )
                Divider()
                AccountSection(
                    modifier = Modifier.weight(2f),
                    loginViewModel = loginViewModel,
                    onNavigate = { screen ->
                        navController.navigate(screen.route) {
                            popUpTo(Screen.Main.route) { inclusive = true }
                        }
                    },
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun StoreSection(
    modifier: Modifier = Modifier,
    onNavigate: (Screen) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = "가게 설정",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        GsTextButtonWithIcon(
            text = "가게 정보 관리하기",
            modifier = Modifier.height(ButtonHeight),
            onClick = { onNavigate(Screen.InputStore(isEditMode = true)) },
        )
        GsTextButtonWithIcon(
            text = "메뉴 정보 관리하기",
            modifier = Modifier.height(ButtonHeight),
            onClick = { onNavigate(Screen.InputMenu(isEditMode = true)) },
        )
    }
}

@Composable
private fun AccountSection(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    onNavigate: (Screen) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = "계정 설정",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

        AccountButton(text = "로그아웃", onClick = {
            loginViewModel.logout()
            onNavigate(Screen.Login)
        })
        AccountButton(text = "회원탈퇴", onClick = {
            loginViewModel.withdraw()
            onNavigate(Screen.Login)
        })
    }
}

@Composable
private fun AccountButton(
    text: String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        content = {
            Text(
                text = text,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "KeyboardArrowRight",
                tint = Color.LightGray,
            )
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier.height(ButtonHeight),
    )
}

@Preview
@Composable
fun SettingScreenPreview() {
    SettingScreen()
}

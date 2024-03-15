package com.erica.gamsung.setting.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erica.gamsung.core.presentation.component.GsOutlinedButton
import com.erica.gamsung.core.presentation.component.GsTextButtonWithIcon
import com.erica.gamsung.core.presentation.component.GsTopAppBar

private val ButtonHeight = 70.dp

@Composable
fun SettingScreen() {
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
                StoreSection(modifier = Modifier.weight(2f))
                Divider()
                AccountSection(modifier = Modifier.weight(2f))
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun StoreSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = "가게 설정",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        GsTextButtonWithIcon(text = "가게 정보 관리하기", modifier = Modifier.height(ButtonHeight))
        GsTextButtonWithIcon(text = "메뉴 정보 관리하기", modifier = Modifier.height(ButtonHeight))
    }
}

@Composable
private fun AccountSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = "계정 설정",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        GsOutlinedButton(
            text = "로그아웃",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
        )
        GsOutlinedButton(
            text = "회원 탈퇴",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
        )
    }
}

@Preview
@Composable
fun SettingScreenPreview() {
    SettingScreen()
}

package com.erica.gamsung.core.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erica.gamsung.core.presentation.component.GsOutlinedButton
import com.erica.gamsung.core.presentation.component.GsTopAppBar

@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            GsTopAppBar(
                title = "메인 페이지",
                hasRightIcon = true,
                onActionsClick = { TODO() },
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            MainButton("글 발행하러 가기")

            Spacer(modifier = Modifier.height(30.dp))

            MainButton("발행 현황 확인하기")
        }
    }
}

@Composable
private fun MainButton(text: String) {
    GsOutlinedButton(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 20.dp),
        text = text,
        fontSize = 25.sp,
        onClick = { TODO() },
    )
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()
}

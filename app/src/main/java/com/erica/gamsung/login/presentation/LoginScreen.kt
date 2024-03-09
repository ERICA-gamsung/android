package com.erica.gamsung.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen() {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(50.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            TitleSection(modifier = Modifier.weight(1f))
            LoginButtonSection(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun TitleSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
    }
}

@Composable
private fun LoginButtonSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}

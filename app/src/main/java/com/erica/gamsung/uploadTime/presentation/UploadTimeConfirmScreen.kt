package com.erica.gamsung.uploadTime.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.core.presentation.Screen

@Preview
@Composable
fun UploadTimeConfirmScreen(navController: NavHostController = rememberNavController()) {
    // val scheduledDataMap = viewModel.scheduleDataMap
    Scaffold {
        Column(
            modifier =
                Modifier
                    .padding(it)
                    .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            ContentSection(modifier = Modifier.weight(2f), onClickEvent = { navController.navigate(Screen.Main.route) })
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun ContentSection(
    modifier: Modifier,
    onClickEvent: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
    ) {
        TextSection(Modifier.weight(1f))
        ActionButton(Modifier.weight(1f), onClick = onClickEvent)
    }
}

@Composable
private fun TextSection(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.weight(1f),
            text = "글이 발행되고 있습니다",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "발행 현황을 확인해 보세요!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Suppress("MagicNumber")
@Composable
private fun ActionButton(
    modifier: Modifier,
    onClick: () -> Unit = {},
) {
    val isSelected = false
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            shape = RoundedCornerShape(12.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor =
                        if (isSelected) {
                            MaterialTheme.colorScheme.secondary
                        } else {
                            MaterialTheme
                                .colorScheme
                                .primary
                        },
                    contentColor =
                        if (!isSelected) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme
                                .colorScheme
                                .onSurface
                        },
                ),
            onClick = {
                !isSelected
                onClick()
                // TODO
            },
        ) {
            Text(text = "메인으로")
        }
    }
}

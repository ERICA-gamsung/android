package com.erica.gamsung.store.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.core.presentation.Screen
import com.erica.gamsung.core.presentation.component.GsButton
import com.erica.gamsung.core.presentation.component.GsTopAppBar

@Composable
fun InputStoreScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        topBar = { GsTopAppBar(title = "식당 정보 입력 (1/2)") },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding(), start = 8.dp, end = 8.dp),
        ) {
            StoreNameSection()
            StoreTypeSection()
            StoreBusinessHoursSection()
            StoreBusinessDaysSeciton()
            StoreAddressSection()
            StorePhoneNumberSection()
            Spacer(modifier = Modifier.weight(1f))
            RegisterStoreButton(onClick = { navController.navigate(Screen.INPUT_MENU.route) })
        }
    }
}

@Composable
fun StoreNameSection() {
    Text(text = "StoreNameSection") // TODO
}

@Composable
fun StoreTypeSection() {
    Text(text = "StoreTypeSection") // TODO
}

@Composable
fun StoreBusinessHoursSection() {
    Text(text = "StoreBusinessHoursSection") // TODO
}

@Composable
fun StoreBusinessDaysSeciton() {
    Text(text = "StoreBusinessDaysSeciton") // TODO
}

@Composable
fun StoreAddressSection() {
    Text(text = "StoreAddressSection") // TODO
}

@Composable
fun StorePhoneNumberSection() {
    Text(text = "StoreAddressSection") // TODO
}

@Composable
private fun RegisterStoreButton(onClick: () -> Unit = {}) {
    GsButton(
        text = "가게 등록하기",
        modifier =
            Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(vertical = 12.dp),
        onClick = onClick,
    )
}

@Preview
@Composable
private fun InputStoreScreenPreveiw() {
    InputStoreScreen()
}

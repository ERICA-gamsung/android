package com.erica.gamsung.store.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erica.gamsung.core.presentation.component.InputTextBox
import com.erica.gamsung.core.presentation.component.TextTitle

@Composable
fun TitleTextField(
    modifier: Modifier = Modifier,
    title: String,
    hintText: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isValid: Boolean = true,
) {
    Column(
        modifier = modifier,
    ) {
        TextTitle(
            title = title,
            isRequired = true,
            description = null,
            modifier = Modifier.padding(vertical = 10.dp),
        )
        InputTextBox(
            modifier = Modifier.fillMaxWidth(),
            hintText = hintText,
            onValueChange = onValueChange,
            keyboardType = keyboardType,
            isError = !isValid,
        )
    }
}

@Preview
@Composable
private fun TitleTextFieldPreview() {
    Column(
        modifier = Modifier.background(Color.White),
    ) {
        TitleTextField(
            title = "음식점 이름",
            hintText = "ex. 감성식당",
            onValueChange = {},
            isValid = true,
            modifier = Modifier.fillMaxWidth(),
        )

        TitleTextField(
            title = "음식점 이름",
            hintText = "ex. 감성식당",
            onValueChange = {},
            isValid = false,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

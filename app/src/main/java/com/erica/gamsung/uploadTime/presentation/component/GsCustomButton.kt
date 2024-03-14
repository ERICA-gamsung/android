package com.erica.gamsung.uploadTime.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CustomInputTextBox(
    modifier: Modifier = Modifier,
    hintText: String,
    onValueChange: (String) -> Unit,
    selectedText: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
) {
//    var textState by remember {
//        mutableStateOf("")
//    }
    OutlinedTextField(
        value = selectedText,
        onValueChange = {
//            textState = it
            onValueChange(it)
        },
        placeholder = {
            Text(
                text = hintText,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        singleLine = true,
        colors =
            TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            ),
        shape = RoundedCornerShape(percent = 15),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = modifier.fillMaxWidth(),
        isError = isError,
    )
}

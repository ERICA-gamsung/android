package com.erica.gamsung.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun GsTextBox(
    hintText: String = "ex)예시 텍스트입니다.",
    title: String,
    description: String?,
    isRequired: Boolean,
) {
    val text by remember {
        mutableStateOf(TextFieldValue(hintText))
    }
    Column {
        Text(
            text =
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        ),
                    ) {
                        append(title)
                    }
                    withStyle(
                        SpanStyle(
                            color = Color.Red,
                        ),
                    ) {
                        if (isRequired) {
                            append(" * ")
                        }
                    }
                    withStyle(
                        SpanStyle(
                            color = Color.LightGray,
                        ),
                    ) {
                        description?.let { append(description) }
                    }
                },
        )
        InputTextBox(text)
    }
}

@Composable
private fun InputTextBox(text: TextFieldValue) {
    var text1 = text
    BasicTextField(
        value = text1,
        onValueChange = {
            text1 = it
        },
        singleLine = true,
        decorationBox = { innerTextField ->
            // Because the decorationBox is used, the whole Row gets the same behaviour as the
            // internal input field would have otherwise. For example, there is no need to add a
            // Modifier.clickable to the Row anymore to bring the text field into focus when user
            // taps on a larger text field area which includes paddings and the icon areas.
            Row(
                Modifier
                    .background(Color.Transparent)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(percent = 15))
                    .padding(16.dp),
            ) {
                innerTextField()
            }
        },
    )
}

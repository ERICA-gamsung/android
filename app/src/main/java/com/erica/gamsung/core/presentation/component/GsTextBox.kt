package com.erica.gamsung.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GsTextBox(
    hintText: String,
    title: String,
    description: String?,
    isRequired: Boolean,
    options: List<String>? = null,
    onOptionSelected: (String) -> Unit,
) {
    val text by remember { mutableStateOf(TextFieldValue(hintText)) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
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
        if (options != null) {
            DropdownTextBox(
                text,
                expanded,
                options,
                selectedOption,
                onExpandedChange = { expanded = !expanded },
                onOptionSelected = { option ->
                    selectedOption = option
                    onOptionSelected.invoke(option)
                    expanded = false
                },
            )
        } else {
            InputTextBox(text)
        }
    }
}

@Composable
private fun DropdownTextBox(
    hintText: TextFieldValue,
    expanded: Boolean,
    options: List<String>,
    selectedOption: String,
    onExpandedChange: () -> Unit,
    onOptionSelected: (String) -> Unit,
) {
    val text = hintText
    BasicTextField(
        value = if (selectedOption.isEmpty()) text else TextFieldValue(selectedOption),
        onValueChange = {},
        singleLine = true,
        readOnly = true,
        modifier =
            Modifier
                .border(1.dp, Color.LightGray, RoundedCornerShape(percent = 15))
                .padding(16.dp)
                .background(Color.Transparent)
                .clickable { onExpandedChange() },
        decorationBox = { innerTextField ->
            Row {
                innerTextField()
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.clickable { onExpandedChange() },
                )
            }
        },
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandedChange() },
    ) {
        options.forEach { label ->
            DropdownMenuItem(
                onClick = {
                    onOptionSelected(label)
                },
                text = {
                    Text(text = label)
                },
            )
        }
    }
}

@Composable
private fun InputTextBox(hintText: TextFieldValue) {
    var text by remember {
        mutableStateOf(hintText)
    }
    var isFocused by remember {
        mutableStateOf(false)
    }
    BasicTextField(
        value = text,
        onValueChange = { updatedText ->
            text = updatedText
        },
        singleLine = true,
        modifier =
            Modifier
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    if (isFocused && text == hintText) {
                        text = TextFieldValue("")
                    } else if (!isFocused && text.text.isEmpty()) {
                        text = hintText
                    }
                }.background(Color.Transparent)
                .border(1.dp, Color.LightGray, RoundedCornerShape(percent = 15))
                .padding(16.dp),
        decorationBox = { innerTextField ->
            // Because the decorationBox is used, the whole Row gets the same behaviour as the
            // internal input field would have otherwise. For example, there is no need to add a
            // Modifier.clickable to the Row anymore to bring the text field into focus when user
            // taps on a larger text field area which includes paddings and the icon areas.
            Row {
                if (!isFocused && text.text.isEmpty()) {
                    Text(hintText.text, color = Color.Gray)
                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    innerTextField()
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        },
    )
}

@Preview
@Composable
private fun GsTextPreview() {
    val options = listOf<String>("option1", "option2", "option3")
    Column(
        modifier = Modifier.background(Color.White),
    ) {
        GsTextBox(
            hintText = "선택 없음",
            title = "강조 메뉴",
            description = "메뉴를 선택하세요",
            isRequired = true,
            options = options,
            onOptionSelected = { option ->
                println("Selected option: $option")
            },
        )
        GsTextBox(
            hintText = "ex) 한양대학교 개강 기념 불고기 10인분",
            title = "이벤트",
            description = " (선택)",
            isRequired = false,
            options = null,
            onOptionSelected = { option ->
                println("Selected option: $option")
            },
        )
        GsTextBox(
            hintText = "",
            title = "고객에게 전달하고 싶은 메세지",
            description = " (선택)",
            isRequired = false,
            options = null,
            onOptionSelected = { option ->
                println("Selected option: $option")
            },
        )
    }
}

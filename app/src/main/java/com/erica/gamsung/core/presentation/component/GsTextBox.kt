package com.erica.gamsung.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
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
    modifier: Modifier,
    innerTextModifier: Modifier,
) {
    val text by remember { mutableStateOf(TextFieldValue(hintText)) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    Column {
        TextTitle(title, isRequired, description)
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
                innerTextModifier = innerTextModifier,
                modifier = modifier,
            )
        } else {
            InputTextBox(
                text,
                innerTextModifier,
                modifier,
            )
        }
    }
}

@Composable
fun TextTitle(
    title: String,
    isRequired: Boolean,
    description: String?,
) {
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
}

@Composable
private fun DropdownTextBox(
    hintText: TextFieldValue,
    expanded: Boolean,
    options: List<String>,
    selectedOption: String,
    onExpandedChange: () -> Unit,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier,
    innerTextModifier: Modifier,
) {
    BasicTextField(
        value = if (selectedOption.isEmpty()) hintText else TextFieldValue(selectedOption),
        onValueChange = {},
        singleLine = true,
        readOnly = true,
        modifier =
            modifier
                .border(
                    1.dp,
                    Color.LightGray,
                    RoundedCornerShape(percent = 15),
                ).background(Color.Transparent)
                .clickable { onExpandedChange() },
        decorationBox = { innerTextField ->
            Row(
                modifier = innerTextModifier,
            ) {
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
fun InputTextBox(
    hintText: TextFieldValue,
    modifier: Modifier,
    innerTextModifier: Modifier,
) {
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
            modifier
                .background(Color.Transparent)
                .border(
                    1.dp,
                    Color.LightGray,
                    RoundedCornerShape(percent = 15),
                ).onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    if (isFocused && text == hintText) {
                        text = TextFieldValue("")
                    } else if (!isFocused && text.text.isEmpty()) {
                        text = hintText
                    }
                },
        decorationBox = { innerTextField ->
            Row(
                modifier = innerTextModifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (!isFocused && text == hintText) {
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
    val options = listOf("option1", "option2", "option3")
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
            modifier = Modifier.fillMaxWidth(1f),
            innerTextModifier = Modifier.padding(16.dp),
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
            modifier = Modifier.fillMaxWidth(1f),
            innerTextModifier = Modifier.padding(16.dp),
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
            modifier = Modifier.fillMaxWidth(1f),
            innerTextModifier = Modifier.padding(16.dp),
        )
    }
}

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
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
    var text by remember { mutableStateOf(TextFieldValue(hintText)) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    Column {
        TextTitle(title, isRequired, description, Modifier)
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
                modifier = innerTextModifier,
                hintText = text.text,
                onValueChange = { text = TextFieldValue(it) },
                keyboardType = KeyboardType.Text,
                isError = false,
            )
        }
    }
}

@Composable
fun TextTitle(
    title: String,
    isRequired: Boolean,
    description: String?,
    modifier: Modifier = Modifier,
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
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier,
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
                .fillMaxWidth()
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
    modifier: Modifier = Modifier,
    hintText: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
) {
    var textState by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = textState,
        onValueChange = {
            textState = it
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownInputTextBox(
    modifier: Modifier = Modifier,
    hintText: String,
    items: List<String>,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
) {
    var textState by remember {
        mutableStateOf("")
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    val icon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = {
                textState = it
                onValueChange(it)
            },
            readOnly = true,
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
            textStyle = MaterialTheme.typography.bodyLarge,
            isError = isError,
            modifier = modifier.menuAnchor().fillMaxWidth(),
            trailingIcon = {
                Icon(icon, "dropDownIcon")
            },
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        textState = label
                        onValueChange(label)
                        expanded = false
                    },
                    text = {
                        Text(text = label)
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun GsTextPreview() {
    val options = listOf("option1", "option2", "option3")
    var text by remember { mutableStateOf("") }
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
        DropdownInputTextBox(
            hintText = "test",
            items = options,
            onValueChange = { newText ->
                text = newText
            },
        )
    }
}

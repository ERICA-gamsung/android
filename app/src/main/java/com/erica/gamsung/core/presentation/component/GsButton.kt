package com.erica.gamsung.core.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val Green = Color(0xFF1F7158)
private val VividBlue = Color(0xFF1268FB)

@Composable
fun GsButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    containerColor: Color = Green,
) {
    Button(
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        content = {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
            )
        },
        modifier = modifier,
    )
}

@Composable
fun GsOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
) {
    OutlinedButton(
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        content = {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        },
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = Color.Black),
    )
}

@Composable
fun GsTextButtonWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    containerColor: Color = VividBlue,
) {
    Button(
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        content = {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "KeyboardArrowRight",
            )
        },
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        modifier = modifier,
    )
}

@Preview
@Composable
private fun GsButtonPreview() {
    Column(
        modifier = Modifier.background(Color.White),
    ) {
        GsButton(text = "가게 등록하기")
        GsOutlinedButton(text = "글 발행하러 가기")
        GsTextButtonWithIcon(
            text = "가게 정보 관리하기",
        )
    }
}

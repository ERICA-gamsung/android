package com.erica.gamsung.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.erica.gamsung.core.presentation.theme.Blue
import com.erica.gamsung.core.presentation.theme.DeepBlue
import com.erica.gamsung.core.presentation.theme.VividBlue

@Composable
fun GsButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    containerColor: Color = Blue,
    enabled: Boolean = true,
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
        enabled = enabled,
    )
}

@Composable
fun GsMainButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    containerColor: Color = DeepBlue,
    fontSize: TextUnit = TextUnit.Unspecified,
    enabled: Boolean = true,
) {
    Button(
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        content = {
            Text(
                text = text,
                fontWeight = FontWeight.Normal,
                fontSize = fontSize,
            )
        },
        modifier = modifier,
        enabled = enabled,
    )
}
//
// @Composable
// fun GsOutlinedButton2(
//    modifier: Modifier = Modifier,
//    text: String,
//    fontSize: Int,
//    onClick: () -> Unit = {},
// ) {
//    Button(
//        onClick = onClick,
//        colors =
//            ButtonDefaults.buttonColors(
//                containerColor = Color.Transparent,
//            ),
//        modifier =
//            modifier
//                .shadow(10.dp, RoundedCornerShape(10.dp))
//                .background(
//                    brush =
//                        Brush.linearGradient(
//                            colors =
//                                listOf(
//                                    Color(0x4DFFFFFF),
//                                    Color(0x33FFFFFF),
//                                ),
//                        ),
//                    shape = RoundedCornerShape(10.dp),
//                ).border(
//                    width = 1.dp,
//                    brush =
//                        Brush.linearGradient(
//                            colors =
//                                listOf(
//                                    Color(0x33FFFFFF),
//                                    Color(0x80FFFFFF),
//                                ),
//                        ),
//                    shape = RoundedCornerShape(10.dp),
//                ),
//    ) {
//        Text(
//            text = text,
//            fontSize = fontSize.sp,
//            color = Color.Black,
//        )
//    }
// }

@Composable
fun GsOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = TextUnit.Unspecified,
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
                fontSize = fontSize,
            )
        },
        modifier = modifier,
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
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
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
//        GsOutlinedButton2(
//            modifier =
//                Modifier
//                    .fillMaxWidth()
//                    .height(60.dp)
//                    .padding(horizontal = 20.dp),
//            text = "text",
//            fontSize = 20,
//        )
    }
}

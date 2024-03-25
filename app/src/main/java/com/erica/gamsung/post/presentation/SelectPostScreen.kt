package com.erica.gamsung.post.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erica.gamsung.core.presentation.component.GsTopAppBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Suppress("MagicNumber")
@Preview
@Composable
fun SelectPostScreen() {
    val serverDate = LocalDate.of(2024, 3, 25)
    val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")
    val date = serverDate.format(formatter)

    val time = "09시 00분"
    val text = dateTextInput(date, time)

    Scaffold(
        topBar = { GsTopAppBar(title = "글 선택 페이지") },
    ) {
        Column(
            modifier =
                Modifier
                    .padding(it)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextSection(Modifier.weight(1f), text = text)
            PicSection(Modifier.weight(5f))
//            Spacer(
//                modifier = Modifier
//                    .weight(1f)
//                    .background(Color.LightGray)
//            )
            ButtonSection(modifier = Modifier.weight(3f))
        }
    }
}

@Composable
fun dateTextInput(
    date: String,
    time: String,
): String {
    val text = "$date ${time}\n 글 선택 중.."
    return text
}

@Composable
fun TextSection(
    modifier: Modifier,
    text: String,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun PicSection(modifier: Modifier) {
    Box(
        // 사진 들어갈 곳
        modifier =
            modifier
                .fillMaxWidth()
                .background(Color.LightGray),
    ) {
        Text(
            text = "게시글",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
fun ButtonSection(modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier =
                Modifier
                    .weight(2f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                onClick = { /* 작성 액션 처리 */ },
                colors =
                    ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(text = "사진 업로드")
            }
            OutlinedButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                onClick = {
                    // 완료 액션 처리
                    // TODO
                },
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(text = "다음", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

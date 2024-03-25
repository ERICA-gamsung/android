package com.erica.gamsung.post.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erica.gamsung.core.presentation.component.GsTopAppBar

@Suppress("MagicNumber")
@Preview
@Composable
fun PreviewPost() {
    Scaffold(
        topBar = { GsTopAppBar(title = "글 미리보기") },
    ) {
        Column(
            modifier = Modifier.padding(it),
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                        ).weight(7f)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp)),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                PicSection(modifier = Modifier.weight(5f))
                TextField(Modifier.weight(2f))
            }
            OneButtonSection(modifier = Modifier.weight(2f))
        }
    }
}

@Composable
fun TextField(modifier: Modifier) {
    Box(
        modifier.padding(16.dp),
    ) {
        Text(text = "안녕하세요! 안녕하세요! 안녕하세요~안녕하세요! 안녕하세요! 안녕하세요~안녕하세요! 안녕하세요! 안녕하세요~안녕하세요! 안녕하세요! 안녕하세요~...")
    }
}

@Composable
fun OneButtonSection(modifier: Modifier) {
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
                Text(text = "완료", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

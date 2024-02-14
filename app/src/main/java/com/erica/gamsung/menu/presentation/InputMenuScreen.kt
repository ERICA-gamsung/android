package com.erica.gamsung.menu.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erica.gamsung.core.presentation.component.GsButton
import com.erica.gamsung.core.presentation.component.GsTopAppBar
import com.erica.gamsung.core.presentation.component.InputTextBox
import com.erica.gamsung.core.presentation.component.TextTitle
import com.erica.gamsung.menu.domain.Menu

@Composable
fun InputMenuScreen() {
    Scaffold(
        topBar = { GsTopAppBar(title = "메뉴 입력 (2/2)") },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
            ) {
                InputMenuSection()
            }
            Divider()
            GsButton(
                text = "메뉴 등록하기",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                onClick = { TODO() },
            )
        }
    }
}

@Suppress("MagicNumber") // TODO 기능 구현 시 제거
@Composable
private fun InputMenuSection() {
    // TODO 기능 구현 시 제거
    val menus =
        listOf(
            Menu("비빔밥", 12000),
            Menu("떡볶이", 8000),
            Menu("김치볶음밥", 9000),
            Menu("갈비구이", 18000),
            Menu("김치전", 13000),
            Menu("해물파전", 15000),
        )

    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(5.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        items(menus) { menu ->
            CompletedMenuItem(menu)
        }

        item {
            InputMenuItem()
        }

        item {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = "메뉴 추가 아이콘",
                )
            }
        }
    }
}

@Composable
private fun CompletedMenuItem(menu: Menu) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(6.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
    ) {
        TitleWithContent(
            title = "메뉴 이름",
            content = menu.name,
            modifier = Modifier.weight(1f),
        )
        TitleWithContent(
            title = "가격",
            content = menu.price.toString(),
            modifier = Modifier.weight(1f),
        )
        IconButton(
            onClick = { /*TODO*/ },
            modifier =
                Modifier
                    .size(20.dp)
                    .padding(top = 5.dp, end = 5.dp),
        ) {
            Icon(
                imageVector = Icons.Default.RemoveCircleOutline,
                contentDescription = "메뉴 제거 아이콘",
            )
        }
    }
}

@Composable
private fun TitleWithContent(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
) {
    Column(
        modifier =
            modifier
                .fillMaxHeight()
                .padding(5.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        TextTitle(
            title = title,
            isRequired = false,
            description = null,
        )
        Text(text = content)
    }
}

@Composable
private fun InputMenuItem() {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(6.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
    ) {
        TitleTextField(
            title = "메뉴 이름",
            hintText = "ex. 고등어 구이 정식",
            modifier =
                Modifier
                    .fillMaxHeight()
                    .padding(5.dp)
                    .weight(1f),
        )
        TitleTextField(
            title = "가격",
            hintText = "ex. 15000",
            modifier =
                Modifier
                    .fillMaxHeight()
                    .padding(5.dp)
                    .weight(1f),
        )
        Icon(
            imageVector = Icons.Default.RemoveCircleOutline,
            contentDescription = "메뉴 제거 아이콘",
            modifier =
                Modifier
                    .size(20.dp)
                    .padding(top = 5.dp, end = 5.dp),
        )
    }
}

@Composable
private fun TitleTextField(
    modifier: Modifier = Modifier,
    title: String,
    hintText: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        TextTitle(title = title, isRequired = true, description = null)
        InputTextBox(
            hintText = TextFieldValue(hintText),
            modifier =
                Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
            innerTextModifier = Modifier.padding(start = 5.dp),
        )
    }
}

@Preview
@Composable
private fun InputMenuScreenPreview() {
    InputMenuScreen()
}

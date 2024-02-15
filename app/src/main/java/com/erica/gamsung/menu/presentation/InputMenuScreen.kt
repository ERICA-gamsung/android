package com.erica.gamsung.menu.presentation

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.core.presentation.Screen
import com.erica.gamsung.core.presentation.component.GsButton
import com.erica.gamsung.core.presentation.component.GsTopAppBar
import com.erica.gamsung.core.presentation.component.InputTextBox
import com.erica.gamsung.core.presentation.component.TextTitle
import com.erica.gamsung.menu.domain.Menu

@Composable
fun InputMenuScreen(navController: NavHostController = rememberNavController()) {
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
            val menus = remember { mutableStateListOf<Menu>() }
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
            ) {
                InputMenuSection(menus)
            }
            Divider()
            GsButton(
                text = "메뉴 등록하기",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                onClick = {
                    // TODO 서버로 메뉴 전송
                    Log.d("InputMenuScreen", "서버로 전송할 메뉴 목록\n ${menus.toList().joinToString("\n")}")

                    navController.navigate(Screen.MAIN.route)
                },
            )
        }
    }
}

@Composable
private fun InputMenuSection(menus: SnapshotStateList<Menu>) {
    val (name, setName) = remember { mutableStateOf("") }
    val (price, setPrice) = remember { mutableStateOf("") }

    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(5.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        itemsIndexed(menus) { index, menu ->
            CompletedMenuItem(menu) { menus.removeAt(index) }
        }

        item {
            InputMenuItem(
                nameChanged = { setName(it) },
                priceChanged = { setPrice(it) },
            )
        }

        item {
            IconButton(onClick = {
                if (name.isNotBlank() && price.isZeroOrPrimitiveInt()) {
                    menus.add(Menu(name, price.toInt()))
                    setName("")
                    setPrice("")
                }
            }) {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = "메뉴 추가 아이콘",
                )
            }
        }
    }
}

private fun String.isZeroOrPrimitiveInt(): Boolean {
    val int = this.toIntOrNull() ?: return false
    return int >= 0
}

@Composable
private fun CompletedMenuItem(
    menu: Menu,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(6.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
    ) {
        MenuItemContainer(
            title = "메뉴 이름",
            content = menu.name,
            modifier = Modifier.weight(1f),
        )
        MenuItemContainer(
            title = "가격",
            content = menu.price.toString(),
            modifier = Modifier.weight(1f),
        )
        IconButton(
            onClick = onClick,
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
private fun MenuItemContainer(
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
            modifier = Modifier,
        )
        Text(text = content)
    }
}

@Composable
private fun InputMenuItem(
    nameChanged: (String) -> Unit,
    priceChanged: (String) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(6.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
    ) {
        TitleTextField(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .padding(5.dp)
                    .weight(1f),
            title = "메뉴 이름",
            hintText = "ex. 고등어 구이 정식",
            onValueChange = nameChanged,
            keyboardType = KeyboardType.Text,
        )
        TitleTextField(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .padding(5.dp)
                    .weight(1f),
            title = "가격",
            hintText = "ex. 15000",
            onValueChange = priceChanged,
            keyboardType = KeyboardType.Number,
        )
        Spacer(modifier = Modifier.padding(10.dp))
    }
}

@Composable
private fun TitleTextField(
    modifier: Modifier = Modifier,
    title: String,
    hintText: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    Column(
        modifier = modifier,
    ) {
        TextTitle(
            title = title,
            isRequired = true,
            description = null,
        )
        InputTextBox(
            hintText = hintText,
            onValueChange = onValueChange,
            keyboardType = keyboardType,
            modifier = Modifier.padding(top = 5.dp, end = 10.dp),
        )
    }
}

@Preview
@Composable
private fun InputMenuScreenPreview() {
    InputMenuScreen()
}

package com.erica.gamsung.core.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GsTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    hasLeftIcon: Boolean = false,
    hasRightIcon: Boolean = false,
    onNavigationClick: () -> Unit = {},
    onActionsClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            // 왼쪽 아이콘
            if (hasLeftIcon) {
                IconButton(
                    onClick = onNavigationClick,
                    content = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "PreviousButton",
                        )
                    },
                )
            }
        },
        actions = {
            // 오른쪽 아이콘
            if (hasRightIcon) {
                IconButton(
                    onClick = onActionsClick,
                    content = {
                        Icon(
                            imageVector = Icons.Rounded.AccountCircle,
                            contentDescription = "SettingButton",
                        )
                    },
                )
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun GsTopAppBarPreview() {
    Column {
        GsTopAppBar(title = "마이 페이지")
        GsTopAppBar(title = "메인 페이지", hasRightIcon = true)
        GsTopAppBar(title = "글 선택 페이지", hasLeftIcon = true)
    }
}

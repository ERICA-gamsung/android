package com.erica.gamsung.core.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun GsTopAppBar(
    title: String = "메인 페이지",
    hasLeftIcon: Boolean = true,
    hasRightIcon: Boolean = true,
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
                            contentDescription = "ArrowBack",
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
                            contentDescription = "AccountCircle",
                        )
                    },
                )
            }
        },
    )
}

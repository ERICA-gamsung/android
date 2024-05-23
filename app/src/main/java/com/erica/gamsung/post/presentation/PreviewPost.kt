package com.erica.gamsung.post.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.erica.gamsung.core.presentation.Screen
import com.erica.gamsung.core.presentation.component.GsTopAppBar

@Suppress("MagicNumber")
@Composable
fun PreviewPost(
    navController: NavHostController,
    postViewModel: PostViewModel = hiltViewModel(),
) {
    val profileImage: Painter = painterResource(id = android.R.drawable.ic_menu_gallery)

    val imgBitmap by postViewModel.imgBitMap.observeAsState()
    val content by postViewModel.content.observeAsState()

    Scaffold(
        topBar = { GsTopAppBar(title = "글 미리보기") },
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding),
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp)),
                ) {
                    // Header with profile image and username
                    ProfileField(
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        profileImage = profileImage,
                    )
                    ImgField(imgBitmap = imgBitmap)
                    Spacer(modifier = Modifier.height(8.dp))
                    PostField(content = content)
                }
            }
        },
        bottomBar = {
            OneButtonSection(
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = {
                    navController.navigate(Screen.SelectNewPost.route) {
                        launchSingleTop = true
                    }
                },
            )
        },
    )
}

@Composable
fun ProfileField(
    modifier: Modifier,
    profileImage: Painter,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Image(
            painter = profileImage,
            contentDescription = null,
            modifier =
                Modifier
                    .size(40.dp)
                    .background(Color.Gray, CircleShape),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "your_username",
            style =
                MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                ),
        )
    }
}

@Composable
fun ImgField(imgBitmap: Bitmap?) {
    imgBitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(
                        top = 8.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ).background(Color.Gray, RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )
    } ?: run {
        Text(
            text = "이미지를 불러오지 못했습니다.",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(
                        top = 8.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ).background(Color.Gray, RoundedCornerShape(8.dp)),
            textAlign = TextAlign.Center,
            color = Color.Red,
        )
    }
}

@Composable
fun PostField(content: String?) {
    Box(
        modifier =
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
    ) {
        content?.let {
            Text(
                text = it,
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                    ),
            )
        } ?: run {
            Text(
                text = "내용을 불러오지 못했습니다.",
                color = Color.Red,
            )
        }
    }
}

@Composable
fun OneButtonSection(
    modifier: Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier =
                Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedButton(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                onClick = onClick,
                border = BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(text = "돌아가기", color = Color.Black)
            }
        }
    }
}

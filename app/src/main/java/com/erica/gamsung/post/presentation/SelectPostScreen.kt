package com.erica.gamsung.post.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.core.presentation.Screen
import com.erica.gamsung.core.presentation.component.GsTopAppBar
import com.erica.gamsung.post.data.mock.Post
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Suppress("MagicNumber")
@Preview
@Composable
fun SelectPostScreen(
    navController: NavHostController = rememberNavController(),
    postViewModel: PostViewModel = viewModel(),
) {
    val serverDate = LocalDate.of(2024, 3, 25)
    val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")
    val date = serverDate.format(formatter)

    val time = "09시 00분"
    val text = dateTextInput(date, time)

    val pagerState =
        rememberPagerState(pageCount = {
            3
        })

    val post = postViewModel.posts.observeAsState(initial = emptyList())
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
            PicSection(Modifier.weight(5f), post, pagerState)

            ButtonSection(modifier = Modifier.weight(3f)) { navController.navigate(Screen.PreviewNewPost.route) }
        }
    }
}

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PicSection(
    modifier: Modifier,
    posts: State<List<Post>>,
    pagerState: PagerState,
) {
    Box(
        // 사진 들어갈 곳
        modifier =
            modifier
                .fillMaxWidth()
                .background(Color.LightGray),
    ) {
        Column {
            HorizontalPager(state = pagerState) { page ->
                PostItem(post = posts.value[page], pageOffset = calculatePageOffset(pagerState, page))
            }
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier =
                            Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(16.dp),
                    )
                }
            }
        }
    }
}
//    Text(
//        text = "게시글",
//        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
//        modifier = Modifier.padding(16.dp),
//    )

@Composable
fun ButtonSection(
    modifier: Modifier,
    onClick: () -> Unit = {},
) {
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
                onClick = onClick,
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(text = "다음", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun calculatePageOffset(
    pagerState: PagerState,
    page: Int,
): Float = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

@Composable
fun PostItem(
    post: Post,
    pageOffset: Float,
) {
    Card(
        Modifier
            .fillMaxSize()
            .graphicsLayer {
                alpha =
                    lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f),
                    )
            }.padding(16.dp),
    ) {
        Column(
            Modifier.padding(16.dp),
        ) {
            Text(text = post.content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

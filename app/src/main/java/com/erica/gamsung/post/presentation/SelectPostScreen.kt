package com.erica.gamsung.post.presentation

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import java.io.InputStream
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

            ButtonSection(
                modifier = Modifier.weight(3f),
                onNextClick = { navController.navigate(Screen.PreviewNewPost.route) },
                // onUploadClick = { imagePickerLauncher.launch("image/*") }
            )
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
    Column(
        modifier = modifier,
    ) {
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            // .padding(bottom = 8.dp),
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
        HorizontalPager(state = pagerState) { page ->
            PostItem(post = posts.value[page], pageOffset = calculatePageOffset(pagerState, page))
        }
    }
}

@Composable
fun ButtonSection(
    modifier: Modifier,
//    onUploadClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
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
            PicBtn()
            OutlinedButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                onClick = onNextClick,
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(text = "다음", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun PicBtn() {
    val selectedImageUri =
        remember {
            mutableStateOf<Uri?>(null)
        }
    val context = LocalContext.current

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) {
            selectedImageUri.value = it
        }
    Button(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 8.dp),
        onClick = { imagePickerLauncher.launch("image/*") },
//                colors =
//                ButtonDefaults.buttonColors(
//                    contentColor = Color.White,
//                    containerColor = MaterialTheme.colorScheme.primary,
//                ),
        shape = RoundedCornerShape(10.dp),
    ) {
        if (selectedImageUri.value == null) {
            Text(text = "사진 업로드")
        } else {
            val inputStream: InputStream? = context.contentResolver.openInputStream(selectedImageUri.value!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "SelectedImage",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop,
            )
        }
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

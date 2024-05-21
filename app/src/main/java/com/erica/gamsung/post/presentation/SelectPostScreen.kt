package com.erica.gamsung.post.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.exifinterface.media.ExifInterface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.erica.gamsung.core.presentation.Screen
import com.erica.gamsung.core.presentation.component.GsTopAppBar
import com.erica.gamsung.post.data.mock.beforeConnectPost
import com.erica.gamsung.post.presentation.utils.formatTime
import java.io.InputStream
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue

@OptIn(
    ExperimentalFoundationApi::class,
)
@Suppress("MagicNumber", "LongMethod")
@Composable
fun SelectPostScreen(
    navController: NavHostController,
    postViewModel: PostViewModel = hiltViewModel(),
    // postViewModel: PostViewModel = viewModel(),
) {
    val reservationId by postViewModel.reservationId.observeAsState()
    val postData by postViewModel.postData.observeAsState()
    val contents by postViewModel.contents.observeAsState(initial = beforeConnectPost)
    val content by postViewModel.content.observeAsState()
    val imgBitmap by postViewModel.imgBitMap.observeAsState()
    val confirmAndNavigate = {
        postViewModel.confirmPostData(reservationId, content, imgBitmap)
        navController.navigate(Screen.PostsStatus.route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = false
            }
        }
    }
    val setContent = { c: String ->
        postViewModel.setContent(c)
    }
    // 진입 시 해당 효과 Launch
    // 서버에 reservationId에 해당하는 데이터를 요청한다
    LaunchedEffect(key1 = true) {
        reservationId?.let { postViewModel.fetchPostData(it) }

        // reservationId?.let { postViewModel.fetchPostDataLocally() }
        Log.d("ResID", "resID: $reservationId")
    }

//    val serverDate = LocalDate.of(2024, 3, 25)
//    val date = serverDate.format(formatter)
//    val time = postData?.time
//    val text = time?.let { dateTextInput(date, it) }
//    val contents = postViewModel.contents.observeAsState(initial = beforeConnectPost)
    val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")
    val date = postData?.date?.format(formatter)
    val time = postData?.let { formatTime(it.time) }
    val text = time?.let { dateTextInput(date, it) }

    // contents의 글은 3개로 제한했기 때문에 3개로 고정한다
    val pagerState =
        rememberPagerState(pageCount = {
            3
        })

    Scaffold(
        topBar = {
            GsTopAppBar(
                title = "글 선택 페이지",
                hasLeftIcon = true,
                onNavigationClick = {
                    navController.navigate(Screen.PostsStatus.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = false
                        }
                    }
                },
            )
        },
        content = { padding ->
            Column(
                modifier =
                    Modifier
                        .padding(padding)
                        .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                if (text != null) {
                    TextSection(Modifier.weight(1f), text = text)
                }
                PicSection(
                    modifier = Modifier.weight(3f),
                    imgBitmap = imgBitmap,
                    onImageSelected = { bitmap ->
                        if (bitmap != imgBitmap) {
                            postViewModel.setImg(bitmap)
                        }
                    },
                )
                PostSection(Modifier.weight(5f), contents, pagerState, setContent)
                ButtonSection(
                    modifier = Modifier.weight(1f),
                    onLeftClick = { navController.navigate(Screen.PreviewNewPost.route) },
                    onRightClick = confirmAndNavigate,
                )
            }
        },
    )
}

fun dateTextInput(
    date: String?,
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
fun PostSection(
    modifier: Modifier,
    posts: List<String>,
    pagerState: PagerState,
    setContent: (String) -> Unit,
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
        if (posts.isNotEmpty()) {
            HorizontalPager(state = pagerState) { page ->
                PostItem(post = posts[page], pageOffset = calculatePageOffset(pagerState, page))
                setContent(posts[page])
            }
        } else {
            // 적절한 메시지 표시 or 다른 UI 요소
            Text("No posts available", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun PicSection(
    modifier: Modifier,
    imgBitmap: Bitmap?,
    onImageSelected: (Bitmap?) -> Unit,
) {
    val context = LocalContext.current
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            selectedImageUri.value = it
        }
    LaunchedEffect(selectedImageUri.value) {
        selectedImageUri.value?.let { uri ->
            val bitmap = loadBitmapFromUri(context = context, uri = uri)
            if (bitmap != null && bitmap != imgBitmap) {
                onImageSelected(bitmap)
            }
        }
    }

    Box(
        modifier =
            modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray.copy(alpha = 0.2f))
                .clickable { imagePickerLauncher.launch("image/*") },
        contentAlignment = Alignment.Center,
    ) {
        if (selectedImageUri.value == null) {
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("사진 업로드")
            }
        } else {
            imgBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            } ?: run {
                Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                    Text("사진 업로드")
                }
            }
        }
    }
}

@Suppress("TooGenericExceptionCaught", "MagicNumber")
private fun loadBitmapFromUri(
    context: Context,
    uri: Uri,
): Bitmap? =
    try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        inputStream.use { stream ->
            val bitmap = BitmapFactory.decodeStream(stream)
            val exif = ExifInterface(context.contentResolver.openInputStream(uri)!!)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
    } catch (e: Exception) {
        Log.e("BitmapFromURi", "$e")
        null
    }

@Composable
fun ButtonSection(
    modifier: Modifier,
    onRightClick: () -> Unit,
    onLeftClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
    ) {
        OutlinedButton(
            modifier =
                Modifier
                    .weight(1f),
            onClick = onLeftClick,
            shape = RoundedCornerShape(8.dp),
            // border = BorderStroke(1.dp, Color.LightGray),
        ) {
            Text(text = "미리보기") // color = Color.Black)
        }
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedButton(
            modifier =
                Modifier
                    .weight(1f),
            onClick = onRightClick,
            shape = RoundedCornerShape(8.dp),
            // border = BorderStroke(1.dp, Color.LightGray),
        ) {
            Text(text = "확정하기") // color = Color.Black)
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
    post: String,
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
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = post, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

// @Composable
// fun PicBtn(
//    //imgUri: Uri?,
//    imgBitmap: Bitmap?,
//    onImageSelected: (Bitmap?) -> Unit
// ) {
//    val context = LocalContext.current
//    val selectedImageUri =
//        remember {
//            mutableStateOf<Uri?>(null)
//        }
//    val imagePickerLauncher =
//        rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.GetContent()
//        ) {
//            selectedImageUri.value = it
//        }
//
//    Button(
//        modifier =
//        Modifier
//            .fillMaxWidth()
//            .height(200.dp)
//            .padding(bottom = 8.dp),
//        onClick = { imagePickerLauncher.launch("image/*") },
//        shape = RoundedCornerShape(10.dp),
//    ) {
//        if (selectedImageUri.value == null) {
//            Text(text = "사진 업로드")
//        } else {
//            Log.d("사진 선택", "성공")
//            val inputStream: InputStream? = context.contentResolver.openInputStream(selectedImageUri.value!!)
//            //onImageSelected(imgUri)
//            val bitmap = BitmapFactory.decodeStream(inputStream)
//            onImageSelected(imgBitmap)
//
//            Image(
//                bitmap = bitmap.asImageBitmap(),
//                contentDescription = "SelectedImage",
//                modifier = Modifier.size(200.dp),
//                contentScale = ContentScale.Crop,
//            )
//        }
//    }
// }

package com.erica.gamsung.core.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erica.gamsung.core.presentation.component.GsOutlinedButton
import com.erica.gamsung.core.presentation.component.GsTopAppBar

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        topBar = {
            GsTopAppBar(
                title = "메인 페이지",
                hasRightIcon = true,
                onActionsClick = { navController.navigate(Screen.Setting.route) },
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Task Progress",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Today, May 24, 2024",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TaskProgressCircle(completedTasks = 4, totalTasks = 6)
            Spacer(modifier = Modifier.height(30.dp))
            MainButton("글 발행하러 가기") { navController.navigate(Screen.DateSelect.route) }

            Spacer(modifier = Modifier.height(30.dp))

            MainButton("발행 현황 확인하기") { navController.navigate(Screen.PostsStatus.route) }
        }
    }
}

@Suppress("MagicNumber")
@Composable
fun TaskProgressCircle(
    completedTasks: Int,
    totalTasks: Int,
    size: Dp = 200.dp,
) {
    val progress = if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f

    Box(
        modifier =
            Modifier
                .size(size)
                .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val strokeWidth = 12.dp.toPx()
            val radius = (size / 2).toPx()
            val center = Offset(radius, radius)
            val gradient =
                Brush.sweepGradient(
                    colors =
                        listOf(
                            Color(0xFF3399FF),
                            Color(0xFF33CCFF),
                            Color(0xFF3399FF),
                        ),
                    center = center,
                )

            // 뒷판 아크
            drawArc(
                color = Color(0xFFF0F0F0),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )

            // 진행률 아크
            drawArc(
                brush = gradient,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$completedTasks/$totalTasks",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Text(
                text = "Schedules",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color.Gray,
            )
        }
    }
}

@Composable
private fun MainButton(
    text: String,
    onClick: () -> Unit = {},
) {
    GsOutlinedButton(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 20.dp),
        text = text,
        fontSize = 25.sp,
        onClick = onClick,
    )
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}

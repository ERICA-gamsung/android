package com.erica.gamsung.login.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.erica.gamsung.R
import com.erica.gamsung.core.presentation.theme.LIGHT_PINK
import com.erica.gamsung.core.presentation.theme.VividBlue

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel()) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(50.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            TitleSection(modifier = Modifier.weight(1f))
            LoginButtonSection(
                modifier = Modifier.weight(1f),
                loginViewModel = loginViewModel,
            )
        }
    }
}

@Composable
private fun TitleSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text =
                buildAnnotatedString {
                    withStyle(style = SpanStyle()) {
                        appendLine("반가워요!")
                        appendLine("오늘부터 가게 마케팅은")
                    }
                    withStyle(SpanStyle(color = VividBlue)) {
                        append("감성과")
                    }
                    withStyle(SpanStyle()) {
                        append(" 함께 해요!")
                    }
                },
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            lineHeight = 40.sp,
        )
    }
}

@Composable
private fun LoginButtonSection(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "3초만에 시작하기", style = MaterialTheme.typography.labelMedium)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        InstagramButton(loginViewModel = loginViewModel)
    }
}

@Composable
private fun InstagramButton(loginViewModel: LoginViewModel) {
    val ctx = LocalContext.current
    Button(
        onClick = {
            val oAuthUrl = loginViewModel.getLoginUrl()
            val urlIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(oAuthUrl),
                )
            ctx.startActivity(urlIntent)
        },
        shape = RoundedCornerShape(50.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = LIGHT_PINK,
                contentColor = Color.Unspecified,
            ),
        modifier =
            Modifier
                .fillMaxWidth()
                .height(50.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.instagram),
            contentDescription = "Instagram Icon",
            modifier = Modifier.size(25.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Instagram으로 시작하기",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}

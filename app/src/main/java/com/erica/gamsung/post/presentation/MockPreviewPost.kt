package com.erica.gamsung.post.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreviewPage(
    userName: String = "your_username",
    profileImage: Painter = painterResource(id = android.R.drawable.ic_menu_gallery),
    postImage: Painter = painterResource(id = android.R.drawable.ic_menu_gallery),
    postText: String = "This is a sample post text for Instagram preview.",
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
    ) {
        // Header with profile image and username
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp),
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
                text = userName,
                style =
                    MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                    ),
            )
        }

        // Post image
        Image(
            painter = postImage,
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Gray, RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Post text
        Text(
            text = postText,
            style =
                MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPagePreview() {
    PreviewPage()
}

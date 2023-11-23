package com.jj.readrover.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Preview
@Composable
fun ReaderSplashScreen(navController: NavController = NavController(context = LocalContext.current)) {
    // 표면을 생성하고, 그 위에 원형 모양을 적용
    Surface(modifier = Modifier
        .padding(15.dp) // 패딩을 적용
        .size(330.dp), // 크기를 설정
        shape = CircleShape, // 원형 모양을 적용
        color = Color.White, // 배경색을 흰색으로 설정
        border = BorderStroke(width = 2.dp, // 테두리의 너비를 설정
            color = Color.LightGray)) { // 테두리의 색상을 설정
        Column(
            modifier = Modifier.padding(1.dp), // 패딩을 적용
            horizontalAlignment = Alignment.CenterHorizontally, // 가로 정렬을 중앙으로 설정
            verticalArrangement = Arrangement.Center // 세로 정렬을 중앙으로 설정
        ) {

            // "ReadRover"라는 텍스트를 추가하고, 스타일을 적용
            Text(text = "ReadRover", style = MaterialTheme.typography.h3,
                color = Color.Red.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(15.dp)) // 공간을 추가
            // 슬로건 텍스트를 추가하고, 스타일을 적용
            Text(text = "\"읽는 것이 여행입니다\"",
                style = MaterialTheme.typography.h5,
                color = Color.LightGray)
        }

    }
}
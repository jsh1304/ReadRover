package com.jj.readrover.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Home(navController: NavHostController) {
    // Scaffold: 앱의 기본 레이아웃을 구성
    Scaffold(
        // TopBar: 앱의 상단 바를 설정
        topBar = {
            // 앱 바를 설정
            ReaderAppBar(title = "ReaderRover", navController = navController)
        },
        // 플로팅 액션 버튼을 설정
        floatingActionButton = {
            FABContent{

            }
        }) {

        // 앱의 내용을 포함하는 표면을 설정
        Surface(modifier = Modifier.fillMaxSize()) {

        }
    }
}

//앱 바를 구성
@Composable
fun ReaderAppBar(
    title: String,
    showProfile: Boolean = true,
    navController: NavHostController
) {

    // TopAppBar: 앱 바의 레이아웃과 스타일을 설정
    TopAppBar(
        title = {
            // Row: 가로 방향으로 요소를 배치
            Row(verticalAlignment = Alignment.CenterVertically) {
                // showProfile이 true인 경우, 프로필 아이콘을 표시
                if (showProfile) {
                    Icon(imageVector = Icons.Default.Favorite,
                        contentDescription = "Logo Icon",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .scale(0.9f))
                }
                // 앱 바의 제목 텍스트를 설정
                Text(text = title,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
            }
        },
        // actions: 앱 바의 액션을 설정
        actions = {},
        // backgroundColor: 앱 바의 배경색을 설정
        backgroundColor = Color.Transparent,
        // elevation: 앱 바의 그림자 높이를 설정
        elevation = 0.dp)
}

// 플로팅 액션 버튼의 내용을 구성
@Composable
fun FABContent(onTap: (String) -> Unit) {
    // FloatingActionButton: 플로팅 액션 버튼의 레이아웃과 스타일을 설정
    FloatingActionButton(onClick = { onTap("") },
        shape = RoundedCornerShape(50.dp),
        backgroundColor = MaterialTheme.colors.background) {
        // Icon: 플로팅 액션 버튼의 아이콘을 설정
        Icon(imageVector = Icons.Default.Add,
            contentDescription = "책 추가",
            tint = MaterialTheme.colors.onSecondary)
    }
}
package com.jj.readrover.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.jj.readrover.model.MBook
import com.jj.readrover.navigation.ReaderScreens

@Preview
@Composable
fun Home(navController: NavController = NavController(LocalContext.current)) {
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
            HomeContent(navController)
        }
    }
}

// 홈 콘텐츠를 보여주는 UI 컴포넌트
@Composable
fun HomeContent(navController: NavController) {

    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty()) // 이메일 주소가 null이 아니고 비어있지 않다면
        FirebaseAuth.getInstance().currentUser?.email?.split("@") // 이메일의 사용자 아이디 생성
            ?.get(0) else
                "N/A" // 이메일 주소가 null이거나 비었다면 "N/A"로 설정
    Column(Modifier.padding(2.dp),
        verticalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "독서현황")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))  // 화면의 가로길이의 70%에 해당하는 공간 만들기
            Column { // 세로로 컴포넌트 나열
                Icon(imageVector = Icons.Filled.AccountCircle, // 프로필 아이콘 컴포넌트
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable { // 클릭시 사용자 독서 통계 화면으로 이동
                            navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colors.secondaryVariant) // 컴포넌트의 색상을 보조 색상으로 설정
                Text(text = currentUserName!!, // 사용자 이름 텍스트 컴포넌트
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.overline, // 텍스트 위에 선이 그어지는 스타일
                    color = Color.Blue,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip) // 텍스트가 주어진 공간을 넘을 경우 잘라냄
                Divider()
            }
        }
    }
}

//앱 바를 구성
@Composable
fun ReaderAppBar(
    title: String,
    showProfile: Boolean = true,
    navController: NavController
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
        actions = {
                  IconButton(onClick = { // 아이콘 클릭 시 사용자 로그아웃
                      FirebaseAuth.getInstance().signOut().run {
                          // 로그아웃 후에 로그인 화면으로 이동
                          navController.navigate(ReaderScreens.LoginScreen.name)
                      }
                  }) {
                      // 로그아웃 아이콘
                    Icon(imageVector = Icons.Filled.Logout,
                        contentDescription = "Logout",
                        tint = Color.Green.copy(alpha = 0.4f))
                  }
        },
        // backgroundColor: 앱 바의 배경색을 설정
        backgroundColor = Color.Transparent,
        // elevation: 앱 바의 그림자 높이를 설정
        elevation = 0.dp)
}

// 타이틀을 보여주는 UI 컴포넌트
@Composable
fun TitleSection(modifier: Modifier = Modifier,
                label: String) {
    Surface(modifier = modifier.padding(start = 5.dp, top = 1.dp)) {
        Column {
            Text(text = label,
                fontSize = 19.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left)
        }
    }
}

@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavHostController) {

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
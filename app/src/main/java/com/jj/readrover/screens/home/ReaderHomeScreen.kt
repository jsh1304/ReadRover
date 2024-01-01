package com.jj.readrover.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.jj.readrover.components.FABContent
import com.jj.readrover.components.ListCard
import com.jj.readrover.components.ReaderAppBar
import com.jj.readrover.components.TitleSection
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

    val listOfBooks = listOf(
        MBook(id = "aaa", title = "헬로 월드", authors = "누군가", notes = null),
        MBook(id = "bbb", title = "굿바이 월드", authors = "누군가", notes = null),
        MBook(id = "ccc", title = "투모로우 월드", authors = "누군가", notes = null),
        MBook(id = "ddd", title = "하이 월드", authors = "누군가", notes = null),
        MBook(id = "eee", title = "리얼 월드", authors = "누군가", notes = null),
    )

    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty()) // 이메일 주소가 null이 아니고 비어있지 않다면
        FirebaseAuth.getInstance().currentUser?.email?.split("@") // 이메일의 사용자 아이디 생성
            ?.get(0) else
                "N/A" // 이메일 주소가 null이거나 비었다면 "N/A"로 설정
    Column(Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top) {
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

        // 현재 읽는 책 보여주는 영역
        ReadingRightNowArea(books = listOf(),
            navController = navController)

        // "독서 리스트" 제목 섹션
        TitleSection(label = "독서 리스트")

        // 책 리스트 영역
        BookListArea(listOfBooks = listOfBooks, navController = navController)
    }
}

// 책 리스트 영역
@Composable
fun BookListArea(listOfBooks: List<MBook>,
                 navController: NavController) {
    HorizontalScrollableComponent(listOfBooks) {
        // TODO: 카드 클릭 시 책 세부사항으로 이동
    }
}

// 수평 스크롤 가능 컴포넌트
@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>, onCardPressed: (String) -> Unit) {
    val scrollState = rememberScrollState() // 스크롤 가능한 컴포넌트, 스크롤 위치를 기억

    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(280.dp)
        .horizontalScroll(scrollState)) {

        for (book in listOfBooks) {
            ListCard(book) {
                onCardPressed(it)
            }
        }
    }
}

// 현재 읽는 책 보여주는 영역
@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController) {

    ListCard() // 책 리스트 카드 불러오기
}







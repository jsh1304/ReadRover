package com.jj.readrover.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.jj.readrover.components.FABContent
import com.jj.readrover.components.ListCard
import com.jj.readrover.components.ReaderAppBar
import com.jj.readrover.components.TitleSection
import com.jj.readrover.model.MBook
import com.jj.readrover.navigation.ReaderScreens

@Preview
@Composable
fun Home(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
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
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }) {

        // 앱의 내용을 포함하는 표면을 설정
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(navController, viewModel)
        }
    }
}

// 홈 콘텐츠를 보여주는 UI 컴포넌트
@Composable
fun HomeContent(navController: NavController, viewModel: HomeScreenViewModel) {
    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.data.value.data!!.toList().filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }
        Log.d("Books", "HomeContent: ${listOfBooks.toString()}")
    }

/*
    val listOfBooks = listOf(
        MBook(id = "aaa", title = "헬로 월드", authors = "누군가", notes = null),
        MBook(id = "bbb", title = "굿바이 월드", authors = "누군가", notes = null),
        MBook(id = "ccc", title = "투모로우 월드", authors = "누군가", notes = null),
        MBook(id = "ddd", title = "하이 월드", authors = "누군가", notes = null),
        MBook(id = "eee", title = "리얼 월드", authors = "누군가", notes = null),
    )*/

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
        ReadingRightNowArea(listOfBooks = listOfBooks,
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
    val addedBooks = listOfBooks.filter { mBook ->
        mBook.startedReading == null && mBook.finishedReading == null
    }
    HorizontalScrollableComponent(addedBooks) {
        // TODO: 카드 클릭 시 책 세부사항으로 이동
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}

// 수평 스크롤 가능한 컴포넌트를 정의하는 함수
@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<MBook>, // 책 목록
    viewModel: HomeScreenViewModel = hiltViewModel(), // 뷰 모델
    onCardPressed: (String) -> Unit // 카드가 눌렸을 때 호출할 콜백 함수
) {
    val scrollState = rememberScrollState() // 스크롤 상태를 기억하는 스테이트

    // 가로로 스크롤 가능한 Row 컴포넌트
    Row(
        modifier = Modifier
            .fillMaxWidth() // 최대 가로 길이까지 채움
            .heightIn(280.dp) // 높이를 최대 280dp로 제한
            .horizontalScroll(scrollState) // 수평 스크롤 적용
    ) {
        // 데이터 로딩 중인 경우
        if (viewModel.data.value.loading == true) {
            LinearProgressIndicator() // 로딩 인디케이터 표시
        } else {
            // 책 목록이 비어 있는 경우
            if (listOfBooks.isNullOrEmpty()) {
                Surface(modifier = Modifier.padding(23.dp)) {
                    // 책을 추가해달라는 안내 메시지 표시
                    Text(
                        text = "책을 추가해주세요.",
                        style = TextStyle(
                            color = Color.Red.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            } else {
                // 책 목록을 순회하며 각각의 책 카드를 표시
                for (book in listOfBooks) {
                    // ListCard 컴포넌트를 호출하여 책 카드 표시
                    ListCard(book) {
                        onCardPressed(book.googleBookId.toString())
                    }
                }
            }
        }
    }
}

// 현재 읽는 책 보여주고, 해당 책들을 가로 스크롤 가능한 컴포넌트에 표시
@Composable
fun ReadingRightNowArea(listOfBooks: List<MBook>,
                        navController: NavController) {
    //Filter books by reading now
    val readingNowList = listOfBooks.filter { mBook ->
        mBook.startedReading != null && mBook.finishedReading == null
    }

    HorizontalScrollableComponent(readingNowList) {
        Log.d("TAG", "BoolListArea: $it")
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}







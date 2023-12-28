package com.jj.readrover.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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


@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavHostController) {

}


// 책 리스트 카드 생성 함수
@Preview
@Composable
fun ListCard(book: MBook = MBook("asd", "kotlin in action", "사람", "hi"),
            onPressDetails: (String) -> Unit = {}) { // onPressDetails는 카드가 클릭되었을 때 호출되는 함수
    val context = LocalContext.current

    // resources: 디바이스의 환경설정, 화면 크기, 밀도 등에 따라 동적으로 변화할 수 있는 값들을 제공
    val resources = context.resources

    // displayMetrics: 디바이스 화면의 여러 가지 메트릭(치수) 정보를 담고 있음.
    // 화면의 너비, 높이, 밀도, 폰트 크기 등을 포함
    val displayMetrics = resources.displayMetrics

    /*
    * 디바이스 화면의 너비를 밀도 독립적인 픽셀(dp) 단위로 변환하는 공식
    * 이렇게 하면 다양한 화면 크기와 해상도를 가진 디바이스에서도 일관된 레이아웃과 디자인을 유지할 수 있음
    * */
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    val spacing = 10.dp

    // 책 카드 생성
    Card(shape = RoundedCornerShape(30.dp),
        backgroundColor = Color.LightGray,
        elevation = 50.dp,
        modifier = Modifier
            .padding(15.dp)
            .height(240.dp)
            .width(200.dp)
            .clickable { onPressDetails.invoke(book.title.toString()) }) { // 클릭 시 책 상세 정보를 제공
        Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start) {
            Row(horizontalArrangement = Arrangement.Center) {
                // 책 이미지 생성
                Image(painter = rememberImagePainter(data = ""),
                    contentDescription = "book image",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp))
                Spacer(modifier = Modifier.width(50.dp)) // 가로 방향으로 50dp 공간 만들기

                Column(modifier = Modifier.padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    // '좋아요' 아이콘
                    Icon(imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Fav Icon",
                        modifier = Modifier.padding(bottom = 1.dp))
                    
                    BookRating(score = 4.0) // 책 평가 점수 표시 함수 호출
                }

            }
            // 책 제목 텍스트
            Text(text = "책 제목", modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis) // Ellipsis: 텍스트가 길어질 시 ..으로 생략

            // 책 글쓴이 텍스트
            Text(text = "글쓴이: 가나다", modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.caption)
        }

    }

}

// 책 평가 점수 표시 함수
@Composable
fun BookRating(score: Double = 4.5) {
    Surface(modifier = Modifier
        .height(70.dp)
        .padding(4.dp),
            shape = RoundedCornerShape(55.dp),
            elevation = 6.dp,
            color = Color.White) {
        Column(modifier = Modifier.padding(4.dp)) {
            // 별 표시 아이콘
            Icon(imageVector = Icons.Filled.StarBorder,
                contentDescription = "Star")

            // 책 평가 점수 아이콘
            Text(text = score.toString(), style = MaterialTheme.typography.subtitle1)
        }
    }
}




package com.jj.readrover.screens.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.jj.readrover.components.ReaderAppBar
import com.jj.readrover.model.MBook
import com.jj.readrover.screens.home.HomeScreenViewModel
import java.util.*

@Composable
fun ReaderStatsScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()) {

    var books: List<MBook>
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold( // 상단 부분
        topBar = {
        ReaderAppBar(title = "책 통계",
            icon = Icons.Default.ArrowBack, // 뒤로가기 버튼
            showProfile = false,
            navController = navController){
            navController.popBackStack() // 뒤로가기 수행
        }
       },
    ) {
        Surface() {
            // 유저가 읽었던 책만을 표시
            books = if(!viewModel.data.value.data.isNullOrEmpty()) {
                viewModel.data.value.data!!.filter { mBook ->
                    (mBook.userId == currentUser?.uid)
                }
            } else {
                emptyList()
            }
            Column {
                // 사용자 정보의 행
                Row {
                    // 사용자 아이콘 표시
                   Box(modifier = Modifier
                       .size(45.dp)
                       .padding(2.dp)) {
                        Icon(imageVector = Icons.Sharp.Person,
                            contentDescription = "icon")
                   }
                    // 사용자에게 인사 텍스트
                    // jj @ naver.com
                    Text(text = "안녕하세요, ${currentUser?.email.toString().split("@")[0]
                        .uppercase(Locale.getDefault())}")
                }
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                    shape = CircleShape,
                    elevation = 5.dp) {

                    // 사용자가 읽은 책리스트
                    // 사용자가 읽은 책리스트가 있다면 필터링, 없다면 빈 리스트를 대입
                    val readBooksList: List<MBook> =
                        if (!viewModel.data.value.data.isNullOrEmpty()) {
                            books.filter { mBook -> // 조건에 맞게 필터링해준다.
                                // 조건 1. 해당 책의 유저아이디와 로그인한 유저아이디 동일 여부
                                // 조건 2. 해당 책 읽기가 완료되있는지 여부
                                (mBook.userId == currentUser?.uid && mBook.finishedReading != null)
                            }
                        } else {
                            emptyList()
                        }

                    // 독서 진행 중인 리스트
                    val readingBooks = books.filter { mBook ->
                        (mBook.startedReading != null && mBook.finishedReading == null)
                    }

                    // 책 통계에 대한 열
                    Column(modifier = Modifier.padding(start = 25.dp, top = 4.dp, bottom = 4.dp),
                        horizontalAlignment = Alignment.Start) {
                        // '책 통계' 텍스트
                        Text(text = "통계", style = MaterialTheme.typography.h5)
                        // 빈 공간
                        Divider()
                        // 독서 진행 중인 책 리스트와 독서 완료한 책 리스트를 표시하는 텍스트
                        Text(text = "독서 진행 중: ${readingBooks.size}권")
                        Text(text = "독서 완료: ${readBooksList.size}권")
                    }
                }
            }
            
            
            
            
        }
    }

}
package com.jj.readrover.screens.update

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.jj.readrover.components.ReaderAppBar
import com.jj.readrover.data.DataOrException
import com.jj.readrover.model.MBook
import com.jj.readrover.screens.home.HomeScreenViewModel

// 책 업데이트 화면 정의
@Composable
fun BookUpdateScreen(
    navController: NavHostController,
    bookItemId: String,
    viewModel: HomeScreenViewModel = hiltViewModel()) {
    
    
    Scaffold(topBar = {
        ReaderAppBar(title = "책 업데이트",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController)
    }) {
        val bookInfo = produceState<DataOrException<List<MBook>,
                    Boolean,
                    Exception>>(initialValue = DataOrException(data = emptyList(),
                                                            true, Exception(""))) {

                        value = viewModel.data.value

        }.value

        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)) {
            Column(
                modifier = Modifier.padding(top = 3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Log.d("INFO", "BookUpdateScreen: ${viewModel.data.value.data.toString()}")

                if (bookInfo.loading == true) {
                    LinearProgressIndicator()
                    bookInfo.loading = false
                } else { // 데이터 로딩이 완료된 경우 책 정보 표시
                    Surface(modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                        shape = CircleShape,
                        elevation = 4.dp) {
                        ShowBookUpdate(bookInfo = viewModel.data.value, bookItemId = bookItemId)
                    }
                }

            }
        }
    }
}

// 책 정보를 표시하는 함수
@Composable
fun ShowBookUpdate(bookInfo: DataOrException<List<MBook>, Boolean, Exception>, // 책 정보와 예외를 가진 상태값
                   bookItemId: String // 업데이트할 책의 아이템 ID
) {
    Row() {
        Spacer(modifier = Modifier.width(43.dp))

        // 책 정보가 null이 아닌 경우
        if (bookInfo.data != null) {
            Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center
            ) {
                // 해당 아이템 ID와 일치하는 책 정보를 찾아 CardListItem으로 전달
                CardListItem(book = bookInfo.data!!.first{mBook ->
                    mBook.googleBookId == bookItemId

                }, onPressedDetails = {})

            }
        }
    }
}


// 책 정보를 표시하는 카드 아이템
@Composable
fun CardListItem(book: MBook, // 표시할 책 정보
                 onPressedDetails: () -> Unit // 상세 정보 버튼 클릭 시 호출할 콜백 함수
) {
    Card(modifier = Modifier
        .padding(
            start = 4.dp,
            end = 4.dp,
            top = 4.dp,
            bottom = 8.dp)
        .clip(RoundedCornerShape(20.dp))
        .clickable { },
        elevation = 8.dp) {

        Row(horizontalArrangement = Arrangement.Start) {
            Image(painter = rememberImagePainter(data = book.photoUrl.toString()),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(120.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(topStart = 120.dp,
                        topEnd = 20.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 0.dp)
                    ))
            Column {
                Text(text = book.title.toString(),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis)

                Text(text = book.authors.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 8.dp,
                        end = 8.dp,
                        top = 3.dp,
                        bottom = 0.dp))

                Text(text = book.publishedDate.toString(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 8.dp,
                        end = 8.dp,
                        top = 0.dp,
                        bottom = 8.dp))
            }
        }

    }
}

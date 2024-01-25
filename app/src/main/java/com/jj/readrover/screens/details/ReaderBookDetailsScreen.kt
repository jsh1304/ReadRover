package com.jj.readrover.screens.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.jj.readrover.components.ReaderAppBar
import com.jj.readrover.data.Resource
import com.jj.readrover.model.Item
import com.jj.readrover.navigation.ReaderScreens

@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    
    Scaffold(topBar = {
        ReaderAppBar(title = "책 상세정보",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController) {
            navController.navigate(ReaderScreens.SearchScreen.name)
        }
    }) {
        Surface(modifier = Modifier
            .padding(3.dp)
            .fillMaxSize()) {

            Column(modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {

                // produceState는 비동기 작업의 결과를 관리리
               val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()){
                    value = viewModel.getBookInfo(bookId) // 책 정보를 가져옴
                }.value

                if (bookInfo.data == null) { // 책 정보 없을 경우
                    Row() {
                        LinearProgressIndicator()
                        Text(text = "로딩 중")
                    }
                } else { // 책 정보 존재 할 경우
                    ShowBookDetails(bookInfo, navController)
                    Text(text = "책 id: ${bookInfo.data.volumeInfo.title}")
                }
                Log.d("TAG", "BookDetailsScreen: ${bookInfo.data.toString()}")
            }
        }

    }
}

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>, navController: NavController) {
    val bookData =  bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id

    Card(modifier = Modifier.padding(34.dp),
        shape = CircleShape,
        elevation = 4.dp) {
        Image(painter = rememberImagePainter(data = bookData!!.imageLinks.thumbnail),
            contentDescription = "Book image",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                )
    }
    
    Text(text = bookData?.title.toString(),
        style = MaterialTheme.typography.h6,
        overflow = TextOverflow.Ellipsis,
        maxLines = 15)
    
    Text(text = "저자: ${bookData?.authors.toString()}")
    Text(text = "쪽수: ${bookData?.pageCount.toString()}")
    Text(text = "카테고리: ${bookData?.categories.toString()}",
        style = MaterialTheme.typography.subtitle1)
    Text(text = "발행일: ${bookData?.publishedDate.toString()}",
        style = MaterialTheme.typography.subtitle1)
}

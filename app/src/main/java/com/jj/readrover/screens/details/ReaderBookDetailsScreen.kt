package com.jj.readrover.screens.details

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
                    LinearProgressIndicator()
                } else { // 책 정보 존재 할 경우
                    Text(text = "책 id: ${bookInfo.data.volumeInfo.title}")
                }
                Log.d("TAG", "BookDetailsScreen: ${bookInfo.data.toString()}")
            }
        }

    }
}
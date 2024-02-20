package com.jj.readrover.screens.update

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
import androidx.navigation.NavHostController
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
                } else {
                    Text(text = viewModel.data.value.data?.get(0)?.title.toString())
                }

            }
        }
    }
}
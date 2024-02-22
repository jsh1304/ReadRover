package com.jj.readrover.screens.update

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.jj.readrover.components.InputField
import com.jj.readrover.components.ReaderAppBar
import com.jj.readrover.data.DataOrException
import com.jj.readrover.model.MBook
import com.jj.readrover.screens.home.HomeScreenViewModel

// 책 업데이트 화면 정의
@ExperimentalComposeUiApi
@Composable
fun BookUpdateScreen(
    navController: NavHostController,
    bookItemId: String,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {


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
                    // 책 정보를 표시하는 서피스 컴포넌트
                    Surface(modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                        shape = CircleShape,
                        elevation = 4.dp) {
                        // 책 정보를 표시하는 컴포넌트 호출
                        ShowBookUpdate(bookInfo = viewModel.data.value, bookItemId = bookItemId)
                    }
                    // 해당 아이템 ID에 해당하는 책을 찾아서 간단한 폼을 표시하는 컴포넌트 호출
                    ShowSimepleForm(book = viewModel.data.value.data?.first { mBook ->
                        mBook.googleBookId == bookItemId
                    }!!, navController)
                }

            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun ShowSimepleForm(book: MBook, navController: NavHostController) {

    // 사용자의 노트 텍스트를 저장하기 위한 상태 변수
    val notesText = remember {
        mutableStateOf("")
    }

    // SimpleForm을 호출하여 사용자 입력을 받음
    SimpleForm(defaultValue = if (book.notes.toString().isNotEmpty()) book.notes.toString()
            else "책에 대한 의견이 없습니다."){ note ->
        notesText.value = note
    }
}

@ExperimentalComposeUiApi
@Composable
fun SimpleForm(
    modifier: Modifier = Modifier, // Modifier를 설정할 수 있는 매개변수
    loading: Boolean = false, // 로딩 상태를 나타내는 매개변수
    defaultValue: String = "최고의 책", // 기본값 설정
    onSearch: (String) -> Unit, // 검색 이벤트를 처리하는 콜백 함수
) {
    // 사용자의 입력을 저장하는 상태 변수
    val textFieldValue = rememberSaveable { mutableStateOf(defaultValue) }
    // 키보드 컨트롤러 가져오기
    val keyboardController = LocalSoftwareKeyboardController.current
    // 입력값이 유효한지 여부를 저장하는 상태 변수
    val valid = remember(textFieldValue.value) { textFieldValue.value.trim().isNotEmpty() }

    // 입력 필드를 구성
    InputField(
        modifier = Modifier
            .height(140.dp)
            .padding(3.dp)
            .background(Color.White, CircleShape)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        valueState = textFieldValue,
        labelId = "당신의 의견을 작성하세요.", // 라벨 텍스트
        enabled = true, // 활성화 여부
        onAction = KeyboardActions{
            // 입력값이 유효하면 검색 이벤트 호출 및 키보드 숨김
            if(!valid) return@KeyboardActions
            onSearch(textFieldValue.value.trim())
            keyboardController?.hide()
        }
    )
}

// 책 정보를 표시하는 함수
@Composable
fun ShowBookUpdate(
    bookInfo: DataOrException<List<MBook>, Boolean, Exception>, // 책 정보와 예외를 가진 상태값
    bookItemId: String, // 업데이트할 책의 아이템 ID
) {
    Row() {
        Spacer(modifier = Modifier.width(43.dp))

        // 책 정보가 null이 아닌 경우
        if (bookInfo.data != null) {
            Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center
            ) {
                // 해당 아이템 ID와 일치하는 책 정보를 찾아 CardListItem으로 전달
                CardListItem(book = bookInfo.data!!.first { mBook ->
                    mBook.googleBookId == bookItemId

                }, onPressedDetails = {})

            }
        }
    }
}


// 책 정보를 표시하는 카드 아이템
@Composable
fun CardListItem(
    book: MBook, // 표시할 책 정보
    onPressedDetails: () -> Unit, // 상세 정보 버튼 클릭 시 호출할 콜백 함수
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

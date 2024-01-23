package com.jj.readrover.screens.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.jj.readrover.components.InputField
import com.jj.readrover.components.ReaderAppBar
import com.jj.readrover.model.Item
import com.jj.readrover.model.MBook
import com.jj.readrover.navigation.ReaderScreens

@ExperimentalComposeUiApi
@Composable
// LocalContext.current인 현재 컨텍스트를 기반으로 NavCotroller를 생성
// 이렇게 하면 @Preview에서도 NavController 사용 가능
fun SearchScreen(
    navController: NavController,
    viewModel: BookSearchViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        ReaderAppBar(title = "책 검색",
            icon = Icons.Default.ArrowBack, // 뒤로가기 아이콘
            navController = navController,
            showProfile = false){
            //navController.popBackStack()
            navController.navigate(ReaderScreens.ReaderHomeScreen.name) // 홈 화면으로 되돌아가기
        }
    }) {
        Surface() {
            Column {
                SearchForm( // 검색 폼 함수
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)) { searchQuery ->
                    viewModel.searchBooks(query = searchQuery)
                }

                Spacer(modifier = Modifier.height(13.dp))
                BookList(navController, viewModel) // 검색에 대한 책 리스트

            }
        }
    }

}

// 검색에 대한 책 리스트
@Composable
fun BookList(navController: NavController, viewModel: BookSearchViewModel) {

    val listOfBooks = viewModel.listOfBooks
    if (viewModel.isLoading){
        LinearProgressIndicator()
    } else {
        // LazyColumn을 활용한 책 리스트
        LazyColumn(modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)) { // 컨텐츠 주변에 패딩 16dp
            // items 함수를 활용 -> 각 책 정보에 대한 행을 생성
            items(items = listOfBooks) { book ->
                BookRow(book, navController) // 책 정보 표시
            }
        }
    }
}

// 하나의 책 정보 표시하는 메소드
@Composable
fun BookRow(book: Item,
            navController: NavController) {
    Card(modifier = Modifier
        .clickable {
            navController.navigate(ReaderScreens.DetailScreen.name + "/${book.id}")
        }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = 7.dp) { // 그림자 깊이 7.dp
        Row(modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top, content = {
                var imageUrl: String = book.volumeInfo?.imageLinks?.let { imageLinks ->
                    if (imageLinks.smallThumbnail?.isEmpty()) {
                        "https://clipart-library.com/2023/249-2496928_nothing-drink-and-drive-png-transparent-png.png"
                    } else {
                        imageLinks.smallThumbnail
                    }
                } ?: "https://clipart-library.com/2023/249-2496928_nothing-drink-and-drive-png-transparent-png.png"

                Image(painter = rememberImagePainter(data = imageUrl),
                    contentDescription = "book image",
                    modifier = Modifier
                        .width(80.dp)
                        .fillMaxHeight()
                        .padding(end = 4.dp))
                // 책 정보를 세로로 나열
                Column() {
                    // 책 제목 텍스트
                    Text(text = book.volumeInfo.title,
                        overflow = TextOverflow.Ellipsis, // Elipsis: 텍스트가 넘치면 ...으로 표기하는 방식
                        maxLines = 3)
                    // 책 작가 텍스트
                    Text(text = "저자: ${(book.volumeInfo.authors?.joinToString() ?:"정보 없음")}",
                        overflow = TextOverflow.Clip, // Clip: 텍스트가 넘치면 잘라내는 방식
                        style = MaterialTheme.typography.caption)
                    // 책 발행일, 카테고리 텍스트
                    Text(text = "${book.volumeInfo.publishedDate} | " +
                            (book.volumeInfo.categories?.joinToString() ?:"카테고리 정보 없음"),
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.caption)
                }
            })

    }
}

// 책 검색 폼 함수
@ExperimentalComposeUiApi
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}) {
    Column() {
        // 사용자의 검색 쿼리를 저장하는 상태 변수
        val searchQueryState = rememberSaveable { mutableStateOf("") }

        // .current를 사용해서 현재 Composable 범위에서 키보드 컨트롤러 인스턴스에 엑세스
        val keyboardController = LocalSoftwareKeyboardController.current

        // searchQueryState.value을 키로 사용하여 'valid' 상태를 기억
        val valid = remember(searchQueryState.value) {
            // .trim() 사용 -> 앞, 뒤 공백 제거
            // isNotEmpty()를 통해 searchQueryState가 비어 있지 않은지 확인
            // 따라서, 결과가 비어이 있지 않을 때 -> 'true
            searchQueryState.value.trim().isNotEmpty()
        }

        // 사용자 입력을 받는 컴포넌트
        InputField(valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            onAction =  KeyboardActions {
                // 'valid'가 false이면 -> 콜백을 종료
                if (!valid) return@KeyboardActions
                // 'onSearch' 함수를 호출하여 검색을 수행
                onSearch(searchQueryState.value.trim())

                // 검색 후 검색 쿼리를 초기화
                searchQueryState.value = ""
                // 검색 후 키보드를 숨김
                keyboardController?.hide()
            })
    }
}
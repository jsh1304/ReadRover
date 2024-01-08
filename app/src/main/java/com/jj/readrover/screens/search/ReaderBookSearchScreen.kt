package com.jj.readrover.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jj.readrover.components.InputField
import com.jj.readrover.components.ReaderAppBar
import com.jj.readrover.navigation.ReaderScreens

@ExperimentalComposeUiApi
@Preview
@Composable
// LocalContext.current인 현재 컨텍스트를 기반으로 NavCotroller를 생성
// 이렇게 하면 @Preview에서도 NavController 사용 가능
fun SearchScreen(navController: NavController = NavController(LocalContext.current)) {
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
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                )

            }
        }
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
        InputField(valuelState = searchQueryState, labelId = "Search", enabled = true,
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
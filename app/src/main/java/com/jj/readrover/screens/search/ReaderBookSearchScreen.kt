package com.jj.readrover.screens.search

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.jj.readrover.components.ReaderAppBar
import com.jj.readrover.navigation.ReaderScreens

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
            navController.navigate(ReaderScreens.ReaderHomeScreen.name) // 홈 화면으로 되돌아가기
        }
    }) {

    }

}
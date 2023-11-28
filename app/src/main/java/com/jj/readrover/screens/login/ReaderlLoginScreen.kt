package com.jj.readrover.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jj.readrover.components.ReaderLogo

// 로그인 화면 구현
@Composable
fun ReaderLoginScreen(navController: NavHostController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            ReaderLogo()
        }
    }
}

// 유저 로그인 정보 입력 Form
@ExperimentalComposeUiApi
@Preview
@Composable
fun UserForm() {
    val email = rememberSaveable { mutableStateOf("") } // 이메일 입력 위한 상태 저장 관리하는 변수
    val password = rememberSaveable { mutableStateOf("") } // 비밀번호 입력 위한 상태 저장 관리하는 변수
    val passwordVisibility = rememberSaveable { mutableStateOf(false) } // 비밀번호 가시성 위한 상태 저장 관리하는 변수
    val passwordFocusRequest = FocusRequester.Default // 비밀번호 필드에 포커스를 요청하는 객체
    val keyboardController = LocalSoftwareKeyboardController.current // 소프트웨어 키보드를 제어하는 컨트롤러
    val valid = remember(email.value, password.value) { // 이메일과 비밀번호의 유효성을 검사하는 변수
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    
    val modifier = Modifier // Modifier 객체 사용하여 UI 요소에 추가할 수 있는 효과를 추가
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState()) // 수직 스크롤이 가능하도록 함
    
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        
    }
}
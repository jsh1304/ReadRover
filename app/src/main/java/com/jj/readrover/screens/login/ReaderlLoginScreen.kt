package com.jj.readrover.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jj.readrover.components.EmailInput
import com.jj.readrover.components.PasswordInput
import com.jj.readrover.components.ReaderLogo

// 로그인 화면 구현
@ExperimentalComposeUiApi
@Composable
fun ReaderLoginScreen(navController: NavHostController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            ReaderLogo()
            UserForm(loading = false, isCreateAccount = false) {email, password ->
                Log.d("Form", "ReaderLoginScreen: $email, $password")
            }
        }
    }
}

// 유저 로그인 정보 입력 Form
@ExperimentalComposeUiApi
@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
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
        EmailInput(emailState = email, enabled = !loading, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        }) // 이메일 입력 필드를 추가하고, 다음 액션으로 비밀번호 필드에 포커스를 요청

        PasswordInput( // PasswordInput 컴포넌트를 생성
            modifier = Modifier.focusRequester(passwordFocusRequest), // 이는 사용자가 이 입력 필드를 선택할 때 포커스를 받도록 함
            passwordState = password, // passwordState는 사용자가 입력한 비밀번호를 저장하는 상태
            labelId = "비밀번호", // 입력 필드의 레이블을 설정
            enabled = !loading, // loading 상태에 따라 입력 필드를 활성화하거나 비활성화. loading이 true이면 입력 필드는 비활성화되고, false이면 활성화
            passwordVisibility = passwordVisibility,  // passwordVisibility는 비밀번호의 가시성을 제어
            onAction = KeyboardActions { // onAction은 키보드 액션이 발생했을 때 실행되는 콜백 함수
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim()) // valid가 true일 때만 onDone 함수를 호출하도록 설정
            }

        )
    }
}



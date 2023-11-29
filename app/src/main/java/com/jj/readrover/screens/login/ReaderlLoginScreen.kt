package com.jj.readrover.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        EmailInput(emailState = email, enabled = true, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        }) // 이메일 입력 필드를 추가하고, 다음 액션으로 비밀번호 필드에 포커스를 요청
    }
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier, // UI 요소에 추가할 수 있는 효과를 추가하는 Modifier 객체
    emailState: MutableState<String>, // 이메일 입력을 위한 상태 저장 변수
    labelId: String = "이메일", // 입력 필드의 레이블 텍스트
    enabled: Boolean = true, // 입력 필드가 활성화 여부를 나타내는 값
    imeAction: ImeAction = ImeAction.Next, // 입력 메서드 액션을 나타내는 객체
    onAction: KeyboardActions = KeyboardActions.Default // 키보드 액션을 처리하는 객체
) {
    InputField(modifier = modifier,
                valuelState = emailState,
                labelId = labelId,
                enabled = enabled,
                keyboardType = KeyboardType.Email,
                imeAction = imeAction,
                onAction = onAction) // 이메일 입력 필드를 구성하는 함수
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valuelState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true, // 입력 필드가 한 줄로 표시되는지 여부를 결정하는 값
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    // 입력 필드를 구성하는 함수
    OutlinedTextField(value = valuelState.value,
        onValueChange = { valuelState.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
                            color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction))


}

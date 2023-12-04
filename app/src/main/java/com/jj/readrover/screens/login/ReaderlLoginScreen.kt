package com.jj.readrover.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jj.readrover.R
import com.jj.readrover.components.EmailInput
import com.jj.readrover.components.PasswordInput
import com.jj.readrover.components.ReaderLogo

// 로그인 화면 구현
@ExperimentalComposeUiApi
@Composable
fun ReaderLoginScreen(navController: NavHostController) {
    // 로그인 폼 표시 여부를 저장하는 상태를 생성
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            // 로고를 표시하는 컴포넌트를 호출
            ReaderLogo()
            // 로그인 폼 표시 여부에 따라 사용자 폼을 표시
            if (showLoginForm.value) UserForm(loading = false, isCreateAccount = false) {email, password ->

            }
            else {
                // 회원가입 폼에서 이메일과 비밀번호를 입력받음
                UserForm(loading = false, isCreateAccount = true) {email, password ->

                }
            }
        }
        // 컴포넌트 간 간격을 조정
        Spacer(modifier = Modifier.height(15.dp))
        Row (
            // 패딩을 적용하여 컴포넌트의 위치를 조정
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 로그인 폼 표시 여부에 따라 텍스트를 변경
            val text = if (showLoginForm.value) "회원가입" else "로그인"
            // 사용자에게 메시지를 표시
            Text(text = "새로운 유저이신가요?")
            // 클릭 가능한 텍스트를 표시하고, 클릭 시 로그인 폼 표시 여부를 토글
            Text(text,
                modifier = Modifier
                    .clickable {
                        showLoginForm.value = !showLoginForm.value
                    }
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.secondaryVariant)
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
        // 계정 생성 여부에 따라 텍스트를 표시
        // 계정 생성 중이면, R.string.create_acct 리소스 ID에 해당하는 문자열을 표시
        if (isCreateAccount) Text(text = stringResource(id = R.string.create_acct),
                                modifier = Modifier.padding(4.dp)) // 텍스트 주변에 4.dp 패딩을 추가
        EmailInput(emailState = email, enabled = !loading, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        }, // 이메일 입력 필드를 추가하고, 다음 액션으로 비밀번호 필드에 포커스를 요청
            )

        PasswordInput( // PasswordInput 컴포넌트를 생성
            modifier = Modifier.focusRequester(passwordFocusRequest), // 이는 사용자가 이 입력 필드를 선택할 때 포커스를 받도록 함
            passwordState = password, // passwordState는 사용자가 입력한 비밀번호를 저장하는 상태
            labelId = "비밀번호", // 입력 필드의 레이블을 설정
            enabled = !loading, // loading 상태에 따라 입력 필드를 활성화하거나 비활성화. loading이 true이면 입력 필드는 비활성화되고, false이면 활성화
            passwordVisibility = passwordVisibility,  // passwordVisibility는 비밀번호의 가시성을 제어
            onAction = KeyboardActions { // onAction은 키보드 액션이 발생했을 때 실행되는 콜백 함수
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim()) // valid가 true일 때만 onDone 함수를 호출하도록 설정
            })
        SubmitButton( // 사용자가 로그인 또는 계정을 만들기 위한 버튼
            textId = if (isCreateAccount) "계정 만들기" else "로그인", // textId는 버튼에 표시될 텍스트
            loading = loading, // loading은 현재 로딩 상태
            validInputs = valid  // validInputs는 입력값의 유효성. 입력값이 유효하지 않으면 버튼이 비활성화
        ) {
            // 버튼을 클릭하면 onDone 함수가 호출되며, 이메일과 비밀번호가 전달
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide() // 활성화된 키보드를 숨김
        }
    }
}

// SubmitButton 함수는 사용자가 클릭할 수 있는 버튼을 생성
@Composable
fun SubmitButton(textId: String, 
                 loading: Boolean, 
                 validInputs: Boolean,
                onClick: () -> Unit) {
    Button(onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,  // 버튼은 로딩 중이 아니고 입력값이 유효할 때만 활성화
        shape = CircleShape
    ) {
        // 로딩 중이면 CircularProgressIndicator가 표시되고, 그렇지 않으면 버튼 텍스트가 표시
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))
        
    }
}



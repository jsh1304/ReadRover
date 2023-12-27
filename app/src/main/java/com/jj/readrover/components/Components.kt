package com.jj.readrover.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.jj.readrover.navigation.ReaderScreens

// 앱 로고 구현
@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(text = "ReadRover",
        modifier = modifier.padding(bottom = 16.dp),
        style = MaterialTheme.typography.h3,
        color = Color.Red.copy(alpha = 0.5f))
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

// 비밀번호 입력 필드를 생성하는 함수
@Composable
fun PasswordInput(
    modifier: Modifier, // modifier는 UI 요소의 레이아웃, 표면, 상호 작용 등을 수정
    passwordState: MutableState<String>, // passwordState는 사용자가 입력한 비밀번호를 저장하는 상태
    labelId: String, // labelId는 입력 필드의 레이블을 설정
    enabled: Boolean, // enabled는 입력 필드의 활성화 상태를 제어
    passwordVisibility: MutableState<Boolean>, // passwordVisibility는 비밀번호의 가시성을 제어하는 상태
    imeAction: ImeAction = ImeAction.Done, // 입력 메서드 액션을 나타내는 객체
    onAction: KeyboardActions // onAction은 키보드 액션이 발생했을 때 실행되는 콜백 함수
) {
    // 비밀번호의 가시성 상태에 따라 시각적 변환을 결정
    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None
    else PasswordVisualTransformation() // 비밀번호를 시각적으로 변환하는 클래스 실행

    // OutlinedTextField 컴포넌트를 사용하여 비밀번호 입력 필드를 생성
    OutlinedTextField(value = passwordState.value, // 입력 필드의 현재 값
        onValueChange = {
            passwordState.value = it  // 사용자가 입력을 변경할 때 호출되는 콜백 함수
        },
        label = { Text(text = labelId)}, // 입력 필드의 레이블
        singleLine = true, // 입력 필드가 한 줄로 제한되는지 여부
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground), // 입력 필드의 텍스트 스타일
        modifier = modifier // 입력 필드의 레이아웃을 수정하는 데 사용되는 modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled, // 입력 필드의 활성화 상태
        keyboardOptions = KeyboardOptions( // 입력 필드의 키보드 옵션
            keyboardType = KeyboardType.Password,
            imeAction = imeAction),
        visualTransformation = visualTransformation, // 입력 필드의 시각적 변환
        trailingIcon = {PasswordVisibility(passwordVisibility = passwordVisibility)}, // 입력 필드의 끝에 표시되는 아이콘
        keyboardActions = onAction) // 입력 필드의 키보드 액션

}

// 비밀번호의 가시성을 제어하는 아이콘을 생성하는 함수
@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value  // 비밀번호의 현재 가시성 상태

    // IconButton 컴포넌트를 사용하여 아이콘 버튼을 생성
    IconButton(onClick = { passwordVisibility.value = !visible }) { // 사용자가 아이콘 버튼을 클릭하면 비밀번호의 가시성 상태를 토글
        Icons.Default.Close
    }
}

//앱 바를 구성
@Composable
fun ReaderAppBar(
    title: String,
    showProfile: Boolean = true,
    navController: NavController
) {

    // TopAppBar: 앱 바의 레이아웃과 스타일을 설정
    TopAppBar(
        title = {
            // Row: 가로 방향으로 요소를 배치
            Row(verticalAlignment = Alignment.CenterVertically) {
                // showProfile이 true인 경우, 프로필 아이콘을 표시
                if (showProfile) {
                    Icon(imageVector = Icons.Default.Favorite,
                        contentDescription = "Logo Icon",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .scale(0.9f))
                }
                // 앱 바의 제목 텍스트를 설정
                Text(text = title,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
            }
        },
        // actions: 앱 바의 액션을 설정
        actions = {
            IconButton(onClick = { // 아이콘 클릭 시 사용자 로그아웃
                FirebaseAuth.getInstance().signOut().run {
                    // 로그아웃 후에 로그인 화면으로 이동
                    navController.navigate(ReaderScreens.LoginScreen.name)
                }
            }) {
                // 로그아웃 아이콘
                Icon(imageVector = Icons.Filled.Logout,
                    contentDescription = "Logout",
                    tint = Color.Green.copy(alpha = 0.4f))
            }
        },
        // backgroundColor: 앱 바의 배경색을 설정
        backgroundColor = Color.Transparent,
        // elevation: 앱 바의 그림자 높이를 설정
        elevation = 0.dp)
}

// 타이틀을 보여주는 UI 컴포넌트
@Composable
fun TitleSection(modifier: Modifier = Modifier,
                 label: String) {
    Surface(modifier = modifier.padding(start = 5.dp, top = 1.dp)) {
        Column {
            Text(text = label,
                fontSize = 19.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left)
        }
    }
}

// 플로팅 액션 버튼의 내용을 구성
@Composable
fun FABContent(onTap: (String) -> Unit) {
    // FloatingActionButton: 플로팅 액션 버튼의 레이아웃과 스타일을 설정
    FloatingActionButton(onClick = { onTap("") },
        shape = RoundedCornerShape(50.dp),
        backgroundColor = MaterialTheme.colors.background) {
        // Icon: 플로팅 액션 버튼의 아이콘을 설정
        Icon(imageVector = Icons.Default.Add,
            contentDescription = "책 추가",
            tint = MaterialTheme.colors.onSecondary)
    }
}
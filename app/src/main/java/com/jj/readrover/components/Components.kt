package com.jj.readrover.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.jj.readrover.model.MBook
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
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction) // 이메일 입력 필드를 구성하는 함수
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true, // 입력 필드가 한 줄로 표시되는지 여부를 결정하는 값
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    // 입력 필드를 구성하는 함수
    OutlinedTextField(value = valueState.value,
        onValueChange = { valueState.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction)

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


// 책 리스트 카드 생성 함수
@Composable
fun ListCard(book: MBook,
             onPressDetails: (String) -> Unit = {}) { // onPressDetails는 카드가 클릭되었을 때 호출되는 함수
    val context = LocalContext.current

    // resources: 디바이스의 환경설정, 화면 크기, 밀도 등에 따라 동적으로 변화할 수 있는 값들을 제공
    val resources = context.resources

    // displayMetrics: 디바이스 화면의 여러 가지 메트릭(치수) 정보를 담고 있음.
    // 화면의 너비, 높이, 밀도, 폰트 크기 등을 포함
    val displayMetrics = resources.displayMetrics

    /*
    * 디바이스 화면의 너비를 밀도 독립적인 픽셀(dp) 단위로 변환하는 공식
    * 이렇게 하면 다양한 화면 크기와 해상도를 가진 디바이스에서도 일관된 레이아웃과 디자인을 유지할 수 있음
    * */
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    val spacing = 10.dp

    // 책 카드 생성
    Card(shape = RoundedCornerShape(30.dp),
        backgroundColor = Color.LightGray,
        elevation = 50.dp,
        modifier = Modifier
            .padding(15.dp)
            .height(240.dp)
            .width(200.dp)
            .clickable { onPressDetails.invoke(book.title.toString()) }) { // 클릭 시 책 상세 정보를 제공
        Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start) {
            Row(horizontalArrangement = Arrangement.Center) {
                // 책 이미지 생성
                Image(painter = rememberImagePainter(data = book.photoUrl.toString()),
                    contentDescription = "book image",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp))
                Spacer(modifier = Modifier.width(50.dp)) // 가로 방향으로 50dp 공간 만들기

                Column(modifier = Modifier.padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    // '좋아요' 아이콘
                    Icon(imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Fav Icon",
                        modifier = Modifier.padding(bottom = 1.dp))

                    BookRating(score = 4.0) // 책 평가 점수 표시 함수 호출
                }

            }
            // 책 제목 텍스트
            Text(text = book.title.toString(), modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis) // Ellipsis: 텍스트가 길어질 시 ..으로 생략

            // 책 글쓴이 텍스트
            Text(text = book.authors.toString(), modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.caption)
        }

        Row(horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom) {
            RoundedButton(label = "독서하기", radius = 70) // 독서 하기 버튼 구현
        }
    }

}


@Composable
fun RoundedButton(
    label: String = "독서하기", // 버튼에 표시될 텍스트
    radius: Int = 29, // 모서리 둥근 정도를 결정하는 반지름
    onPress: () -> Unit = {}) { // 버튼 클릭 시 실맹될 함수
    Surface(modifier = Modifier.clip(RoundedCornerShape( // clip을 사용하여 모서리가 둥근 모양을 적용
        bottomEndPercent = radius, // 하단 오른쪽 모서리의 둥근 정도를 설정
        topStartPercent = radius)), // 상단 원쪽 모서리의 둥근 정도를 설정
        color = Color(0xFF92CBDF)) {

        Column(modifier = Modifier
            .width(90.dp)
            .heightIn(40.dp) // Column의 최소 높이를 설정
            .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = TextStyle(color = Color.White, fontSize = 15.sp))
        }
    }
}

//앱 바를 구성
@Composable
fun ReaderAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onBackArrowClicked:() -> Unit = {}
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
                if (icon != null) { // 아이콘이 존재한다면
                    // 아이콘 클릭 시 onBackArrowCliecked 실행
                    Icon(imageVector = icon, contentDescription = "arrow back",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.clickable { onBackArrowClicked.invoke() })
                }
                Spacer(modifier = Modifier.width(45.dp))
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
                if (showProfile) Row() {
                    // 로그아웃 아이콘
                    Icon(imageVector = Icons.Filled.Logout,
                        contentDescription = "Logout",
                        tint = Color.Green.copy(alpha = 0.4f)) // 투명도를 0.4로 설정
                } else Box {} // Box 레이아웃을 생성

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


// 책 평가 점수 표시 함수
@Composable
fun BookRating(score: Double = 4.5) {
    Surface(modifier = Modifier
        .height(70.dp)
        .padding(4.dp),
        shape = RoundedCornerShape(55.dp),
        elevation = 6.dp,
        color = Color.White) {
        Column(modifier = Modifier.padding(4.dp)) {
            // 별 표시 아이콘
            Icon(imageVector = Icons.Filled.StarBorder,
                contentDescription = "Star")

            // 책 평가 점수 아이콘
            Text(text = score.toString(), style = MaterialTheme.typography.subtitle1)
        }
    }
}

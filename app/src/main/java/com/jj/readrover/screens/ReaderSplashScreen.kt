package com.jj.readrover.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jj.readrover.components.ReaderLogo
import com.jj.readrover.navigation.ReaderScreens
import kotlinx.coroutines.delay

@Preview
@Composable
fun ReaderSplashScreen(navController: NavController = NavController(context = LocalContext.current)) {

    val scale = remember { // 스케일 애니메이션 위한 초기값을 0f로 설정
        androidx.compose.animation.core.Animatable(0f)
    }

    // LaunchedEffect를 사용하여 코루틴 스코프를 생성. Key1이 true일 때만 실행
    LaunchedEffect(key1 = true){
        scale.animateTo(targetValue = 0.9f, // 애니매이션을 시작. 목표값은 0.9f
                        animationSpec = tween(durationMillis = 800, // 애니메이션 지속 시간은 800ms
                                            easing = { // 애니메이션의 속도 변화를 제어
                                                // OvershootInterpolator를 사용하여 애니메이션의 가속도를 조절
                                                OvershootInterpolator(8f) // tension이 커질수록 애니메이션 반동이 커짐
                                                    .getInterpolation(it) // it은 애니메이션의 현재 진행 상황을 나타냄
                                            }))
        // 애니메이션 끝난 후 2초동안 대기
        delay(2000L)

        navController.navigate(ReaderScreens.LoginScreen.name) // 로그인 화면으로 이동
    }


    // 표면을 생성하고, 그 위에 원형 모양을 적용
    Surface(modifier = Modifier
        .padding(15.dp) // 패딩을 적용
        .size(330.dp) // 크기를 설정
        .scale(scale.value), // 스케일 설정
        shape = CircleShape, // 원형 모양을 적용
        color = Color.White, // 배경색을 흰색으로 설정
        border = BorderStroke(width = 2.dp, // 테두리의 너비를 설정
            color = Color.LightGray)) { // 테두리의 색상을 설정
        Column(
            modifier = Modifier.padding(1.dp), // 패딩을 적용
            horizontalAlignment = Alignment.CenterHorizontally, // 가로 정렬을 중앙으로 설정
            verticalArrangement = Arrangement.Center // 세로 정렬을 중앙으로 설정
        ) {

            // "ReadRover"라는 텍스트를 추가하고, 스타일을 적용
            ReaderLogo() // 앱 로고 불러오기
            Spacer(modifier = Modifier.height(15.dp)) // 공간을 추가
            // 슬로건 텍스트를 추가하고, 스타일을 적용
            Text(text = "\"읽는 것이 여행입니다\"",
                style = MaterialTheme.typography.h5,
                color = Color.LightGray)
        }

    }
}


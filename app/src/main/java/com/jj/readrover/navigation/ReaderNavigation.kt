package com.jj.readrover.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jj.readrover.screens.ReaderSplashScreen
import com.jj.readrover.screens.home.Home
import com.jj.readrover.screens.login.ReaderLoginScreen

@ExperimentalComposeUiApi
@Composable
fun ReaderNavigation() {
    val navController = rememberNavController() // 화면 간의 탐색을 관리
    NavHost(navController = navController,
    startDestination = ReaderScreens.SplashScreen.name){ // NavHost를 생성하고 시작 화면으로 SplashScreen을 설정
        composable(ReaderScreens.SplashScreen.name){
            ReaderSplashScreen(navController = navController) // 스플래시 화면 실행
        }

        composable(ReaderScreens.LoginScreen.name){ // 로그인 화면 실행
            ReaderLoginScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name){ // 홈 화면 실행
            Home(navController = navController)
        }
    }
}
package com.jj.readrover.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jj.readrover.screens.ReaderSplashScreen
import com.jj.readrover.screens.home.Home

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController() // 화면 간의 탐색을 관리
    NavHost(navController = navController,
    startDestination = ReaderScreens.SplashScreen.name){
        composable(ReaderScreens.SplashScreen.name){
            ReaderSplashScreen(navController = navController) // 스플래시 화면 실행
        }

        composable(ReaderScreens.ReaderHomeScreen.name){
            Home(navController = navController)
        }
    }
}
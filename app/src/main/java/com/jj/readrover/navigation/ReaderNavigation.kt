package com.jj.readrover.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.jj.readrover.screens.ReaderSplashScreen
import com.jj.readrover.screens.details.BookDetailsScreen
import com.jj.readrover.screens.home.Home
import com.jj.readrover.screens.login.ReaderLoginScreen
import com.jj.readrover.screens.search.BookSearchViewModel
import com.jj.readrover.screens.search.SearchScreen
import com.jj.readrover.screens.stats.ReaderStatsScreen

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

        composable(ReaderScreens.ReaderStatsScreen.name){ // 사용자 독서 통계 화면 실행
            ReaderStatsScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name){ // 홈 화면 실행
            Home(navController = navController)
        }

        composable(ReaderScreens.SearchScreen.name){ // 책 검색 화면 실행
            val searchViewModel = hiltViewModel<BookSearchViewModel>()
            SearchScreen(navController = navController, viewModel = searchViewModel)
        }

        val detailName = ReaderScreens.DetailScreen.name

        // $detailName/{bookId}를 경로로 사용
        // "bookId"라는 인수를 정의
        composable("$detailName/{bookId}", arguments = listOf(navArgument("bookId"){ // 책 상세정보 화면 실행
            type = NavType.StringType // 인수의 유형을 StringType으로 설정
        })){ backStackEntry ->
            // "bookId" 인수의 값을 가져옴
            // BookDetailsScreen 컴포저블을 호출하여 책의 상세 정보를 표시
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(navController = navController, bookId = it.toString())
            }
        }
    }
}
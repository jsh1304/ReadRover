package com.jj.readrover.navigation

import java.lang.IllegalArgumentException

// 리더기 애플리케이션의 다양한 화면을 나타나는 Enum 클래스
enum class ReaderScreens {
    SplashScreen, // 스플래시 화면
    LoginScreen, // 로그인 화면
    CreateAccountScreen, // 계정 생성 화면
    ReaderHomeScreen, // 리더 홈 화면
    SearchScreen, // 검색 화면
    DetailScreen, // 상세 화면
    UpdateScreen, // 업데이트 화면
    ReaderStatsScreen; // 리더 통계 화면

    companion object {
        /**
         * 주어진 경로로부터 ReaderScreen 생성
         * 경로가 없다면 (null), 기본적으로 '리더 홈 화면'을 반환
         * route가 인식되지 않는다면 (else), IllegalArgumentException을 발생 (메서드에 부적절한 인수가 전달됐을 때 발생하는 예외)
         */
        fun fromRoute(route: String?): ReaderScreens
         = when(route?.substringBefore("/")) {
             SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            SearchScreen.name -> SearchScreen
            DetailScreen.name -> DetailScreen
            UpdateScreen.name -> UpdateScreen
            ReaderStatsScreen.name -> ReaderStatsScreen
            null -> ReaderHomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")


         }
    }
}
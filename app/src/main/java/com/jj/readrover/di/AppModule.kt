package com.jj.readrover.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module // Dagger 모듈. 의존성을 제공하는 메소드를 포함
@InstallIn(SingletonComponent::class) // 모듈을 SingletonComponent에 설치하도록 지정
object AppModule {
}
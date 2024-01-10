package com.jj.readrover.di

import com.jj.readrover.network.BooksApi
import com.jj.readrover.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module // Dagger 모듈. 의존성을 제공하는 메소드를 포함
@InstallIn(SingletonComponent::class) // 모듈을 SingletonComponent에 설치하도록 지정
object AppModule {

    @Singleton
    @Provides
    fun provideBookApi(): BooksApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)

    }
}
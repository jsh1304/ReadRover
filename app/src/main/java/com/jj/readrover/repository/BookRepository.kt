package com.jj.readrover.repository

import com.jj.readrover.data.DataOrException
import com.jj.readrover.data.Resource
import com.jj.readrover.model.Item
import com.jj.readrover.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BooksApi) {


    // 검색 쿼리를 기반으로 책 목록을 가져오는 함수
    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {
        return try {
            // 작업 진행 중인 로딩 상태를 나타냄
            Resource.Loading(data = "로딩...")

            // 책 검색한 결과값을 저장한 리스트
            val itemList = api.getAllBooks(searchQuery).items

            // 책 검색 리스트 값이 존재하면 로딩 종료
            if (itemList.isNotEmpty()) Resource.Loading(data = false)

            // 작업 성공. 책 검색 리스트를 반환
            Resource.Success(data = itemList)
        } catch (exception: Exception) { // 예외 처리
            // 오류 발생에 따른 메시지 반환
            Resource.Error(message = exception.message.toString())
        }


    }

    // 책 고유 id를 사용하여 특정 책의 정보를 가져오는 함수
    suspend fun getBookInfo(bookId: String): Resource<Item> {
        val response = try {
            // 로딩 상태
            Resource.Loading(data = true)

            // 해당 bookId의 책 정보를 가져옴
            api.getBookInfo(bookId)

        } catch (exception: Exception) { // 예외 처리
            return Resource.Error(message = "에러 발생 ${exception.message.toString()}")
        }
        // 로딩 종료
        Resource.Loading(data = false)

        // 책 정보에 대해 반환
        return Resource.Success(data = response)

    }
}
package com.jj.readrover.repository

import com.jj.readrover.data.DataOrException
import com.jj.readrover.model.Item
import com.jj.readrover.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BooksApi) {
    // 책 목록을 관리하는 변수. DataOrException 클래스의 인스턴스를 참조
    private val dataOrException =
        DataOrException<List<Item>, Boolean, Exception>()

    // 특정 책 정보를 관리하는 변수. DataOrException 클래스의 인스턴스를 참조
    private val bookInfoDataOrException =
        DataOrException<Item, Boolean, Exception>()

    // 검색 쿼리를 기반으로 책 목록을 가져오는 함수
    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>,
            Boolean, Exception> {
        try {
            dataOrException.loading = true // 로딩 시작
            dataOrException.data = api.getAllBooks(searchQuery).items // API 호출
            if (dataOrException.data!!.isNotEmpty()) dataOrException.loading = false // 로딩 종료
        } catch (e: Exception) {}  // 예외 처리

        return dataOrException

    }

    // 책 고유 id를 사용하여 특정 책의 정보를 가져오는 함수
    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        val response = try {
            bookInfoDataOrException.loading = true // 로딩 시작
            bookInfoDataOrException.data = api.getBookInfo(bookId = bookId) // API 호출
            if (bookInfoDataOrException.data.toString().isNotEmpty()) dataOrException.loading = false // 로딩 종료
            else {}
        } catch (e: Exception) {
            bookInfoDataOrException.e = e // 예외 처리
        }
        return bookInfoDataOrException // 결과 반환
    }
}
package com.jj.readrover.network

import com.jj.readrover.model.Book
import com.jj.readrover.model.Item
import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {

    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Book

    @GET("volumes/{bookId")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Item

}
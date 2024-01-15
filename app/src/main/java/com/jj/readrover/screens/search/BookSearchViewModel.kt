package com.jj.readrover.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.readrover.data.DataOrException
import com.jj.readrover.model.Item
import com.jj.readrover.repository.BookRepository
import com.squareup.okhttp.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BookRepository):
ViewModel(){
    val listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>>
        = mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                return@launch
            }
            listOfBooks.value.loading = true // 로딩 실행
            listOfBooks.value = repository.getBooks(query) // 검색 쿼리에 대한 값을 저장
            Log.d("Search", "searchBooks: ${listOfBooks.value.data.toString()}")

            // 검색 쿼리에 대한 결과 값이 존재하면 로딩 종료
            if (listOfBooks.value.data.toString().isNotEmpty()) listOfBooks.value.loading = false

        }
    }

}
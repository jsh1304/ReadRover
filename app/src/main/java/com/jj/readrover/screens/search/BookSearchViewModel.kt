package com.jj.readrover.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.readrover.data.Resource
import com.jj.readrover.model.Item
import com.jj.readrover.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BookRepository):
ViewModel(){
    var listOfBooks: List<Item> by mutableStateOf(listOf())

    init {
        loadBooks()
    }

    private fun loadBooks() {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                return@launch
            }
            try {
                when(val response = repository.getBooks(query)) {
                    is Resource.Success -> {
                        listOfBooks = response.data!!
                    }
                    is Resource.Error -> {
                        Log.e("Network", "searchBooks: 책 검색 실패", )
                    }
                    else -> {}
                }
            } catch (exception: Exception) {
                Log.d("Network", "searchBooks: ${exception.message.toString()}")
            }

        }
    }

}
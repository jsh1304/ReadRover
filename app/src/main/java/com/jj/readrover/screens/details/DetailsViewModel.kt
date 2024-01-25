package com.jj.readrover.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.readrover.data.Resource
import com.jj.readrover.model.Item
import com.jj.readrover.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository) // BookRepository 주입
    :ViewModel(){
    // 책 정보를 가져오는 메서드
    // 코루틴 사용하여 비동기 작업 수행
        suspend fun getBookInfo(bookId: String): Resource<Item> {
            return repository.getBookInfo(bookId)
        }
}
package com.jj.readrover.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.readrover.data.DataOrException
import com.jj.readrover.model.MBook
import com.jj.readrover.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: FireRepository): ViewModel() {
        val data: MutableState<DataOrException<List<MBook>, Boolean, Exception>>
            = mutableStateOf(DataOrException(listOf(), true, Exception("")))

    init {
        getAllBooksFromDataBase() // 모든 책 가져오기 메서드 실행
    }

    // DB로부터 저장된 모든 책 가져오기 메서드
    private fun getAllBooksFromDataBase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllBooksFromDatabase()
            if (!data.value.data.isNullOrEmpty()) data.value.loading = false
        }
        Log.d("GET", "getAllBooksFromDataBase: ${data.value.data?.toList().toString()}")
    }
}
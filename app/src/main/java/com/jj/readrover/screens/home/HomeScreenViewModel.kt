package com.jj.readrover.screens.home

import androidx.lifecycle.ViewModel
import com.jj.readrover.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: FireRepository
                                    ): ViewModel() {
}
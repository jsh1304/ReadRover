package com.jj.readrover.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginScreenViewModel: ViewModel() {
    //val loadingDtate = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWitEmailAndPassword(email: String, password: String)
    = viewModelScope.launch{
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){

                    } else {
                        Log.d("FB", "signInWitEmailAndPassword: ${task.result.toString()}")
                    }
                }
        } catch (ex: Exception) {
            Log.d("FB", "signInWitEmailAndPassword: ${ex.message}")
        }
    }

}
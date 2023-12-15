package com.jj.readrover.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.lang.Exception

// 로그인 화면의 뷰 모델을 나타냄
class LoginScreenViewModel: ViewModel() {
    //val loadingDtate = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth // Firebase 인증 객체

    /**
     * LiveDate는 값을 관찰하고 사용 O, 값 변경 X
     * MutableLiveDate는 값 변경 O
     */
    // 로딩 상태를 나타냄
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    // 이메일과 비밀번호로 로그인을 시도
    fun signInWitEmailAndPassword(email: String, password: String, home: () -> Unit)
    = viewModelScope.launch{
        try {
            // Firebase 인증을 사용하여 이메일과 비밀번호로 로그인을 시도
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){ // 작업이 성공적으로 완료하면
                        Log.d("FB", "signInWitEmailAndPassword: success ${task.result.toString()}")
                        home() // 홈 화면 호출
                    } else { // 작업이 실패하면
                        Log.d("FB", "signInWitEmailAndPassword: fail ${task.result.toString()}")
                    }
                }
        } catch (ex: Exception) { // 예외가 발생하면 예외 메시지를 로그로 출력
            Log.d("FB", "signInWitEmailAndPassword: ${ex.message}")
        }
    }

}
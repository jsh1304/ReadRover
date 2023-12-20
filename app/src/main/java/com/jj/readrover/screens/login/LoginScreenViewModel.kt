package com.jj.readrover.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.jj.readrover.model.MUser
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


    // 회원가입 메서드
    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit) {

        // 로딩 중 x -> 회원가입 시도
        if (_loading.value == false) {
            _loading.value = true // 로딩 상태를 true로 변경
            auth.createUserWithEmailAndPassword(email, password) // Firebase auth를 사용 -> 이메일, 비번으로 사용자 생성
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) { // 작업이 성공하면
                        // 아이디@이메일.com의 아이디 부분
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        createUser(displayName) // 유저 정보 생성
                        home() // home 함수 실행
                    } else { // 작업이 실패하면 로그에 메시지 출력
                        Log.d("FB", "createUserWithEmailAndPassword: ${task.result}")
                    }
                    _loading.value = false // 작업이 완료 -> 로딩 상태를 false로 변경
                }
        }
    }

    // Firestore에 유저 정보 추가 메서드
    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid // 현재 사용자의 인증 uid
        val user = MUser(userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Life is good",
            profession = "안드로이드 개발자",
            id = null).toMap() // MUser 객체 속성들을 Map 형태로 변환한 값


        // "users" 컬렉션에 user 정보 추가
        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }

}
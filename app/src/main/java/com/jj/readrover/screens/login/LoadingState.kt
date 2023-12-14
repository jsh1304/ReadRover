package com.jj.readrover.screens.login

// 로딩 상태를 나타내는 데이터 클래스
data class LoadingState(val statue: Status, val message: String? = null) {

    // 동반 객체에서는 상태에 대한 기본값을 정의
    companion object {
        val IDLE = LoadingState(Status.IDLE) // 아무 작업도 수행하지 않은 상태
        val SUCCESS = LoadingState(Status.SUCCESS) // 작업이 성공적으로 완료된 상태
        val LOADING = LoadingState(Status.LOADING) // 작업이 진행 중인 상태
        val FAILED = LoadingState(Status.FAILED) // 작업이 실패한 상태
    }

    // Status 열거형은 가능한 모든 상태를 나열
    enum class Status {
        SUCCESS, // 작업이 성공적으로 완료
        FAILED, // 작업이 실패
        LOADING, // 작업이 진행 중
        IDLE // 아무 작업도 수행되지 않음
    }

}

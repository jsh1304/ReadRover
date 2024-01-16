package com.jj.readrover.data

// 속성 : data, message
sealed class Resource<T>(val data: T?, val message: String? = null){
    // Success, Error, Loading은 Resource의 하위 클래스

    // 성공적인 작업을 나타내며, data를 반환
    class Success<T>(data: T?): Resource<T>(data)

    // 오류 발생을 나타내며, 오류 메시지와 선택적으로 data를 반환
    class Error<T>(message: String?, data: T? = null): Resource<T>(data, message)

    // 작업이 진행 중임을 나타내며, 선택적으로 data를 반환
    class Loading<T>(data: T? = null): Resource<T>(data)
}

package com.jj.readrover.data

data class DataOrException<T, Boolean, E: Exception>(
    var data: T? = null, // 데이터를 나타내는 변수. 제네릭 타입 'T'
    var loading: kotlin.Boolean? = null, // 로딩 상태를 나타내는 변수
    var e: E? = null) // 예외를 나타내는 변수. 제네릭 타입 'E'

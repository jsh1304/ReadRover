package com.jj.readrover.model

data class MUser(val id: String?, // 사용자의 고유 식별자
                val userId: String, // 사용자의 id
                val displayName: String, // 사용자의 표시 이름
                val avatarUrl: String, // 사용자의 아바타 이미지 URL
                val quote: String, // 사용자의 인용구
                val profession: String) { // 사용자의 직업
    fun toMap(): MutableMap<String, Any> { // 'MUser' 객체의 속성들을 'MutableMap<String, Any>' 형태로 반환
        return mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "quote" to this.quote,
            "profession" to this.profession,
            "avatar_url" to this.avatarUrl
        )
    }
}
